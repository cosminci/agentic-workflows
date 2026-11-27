import argparse
from collections import Counter
from pathlib import Path
import re
import sys


MESSAGES = {
    "assertion": "Multiline assertion: look for setup or actions inside the check. "
    "Name large operands only when that makes the full outcome clearer.",
    "exception": "Multiline exception assertion: look for setup that can throw first. "
    "Prepare inputs outside; check only the operation under test.",
}
SUFFIX = " Fix the cause or justify a rule-specific inline exclusion."
DIAGNOSTIC = re.compile(r"^.*[/\\]([^/\\]+\.scala):(\d+):(\d+): (error): (.*)$")
MARKER = re.compile(r" // (registered )?(assertion|exception)$")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("log", type=Path)
    parser.add_argument("--registered", action="store_true")
    args = parser.parse_args()
    expected = Counter()
    sources = Path(__file__).resolve().parent / "src/test/scala"
    for source in (sources / "Assertions.scala", sources / "ExtendedAssertions.scala"):
        for number, line in enumerate(source.read_text().splitlines(), 1):
            marker = MARKER.search(line)
            if marker and (args.registered or not marker[1]):
                column = len(line) - len(line.lstrip()) + 1
                message = "[MultilineAssertion] " + MESSAGES[marker[2]] + SUFFIX
                expected[source.name, number, column, "error", message] += 1

    log = args.log.read_text()
    actual = Counter(
        (match[1], int(match[2]), int(match[3]), match[4], match[5])
        for line in log.splitlines()
        if (match := DIAGNOSTIC.match(line))
    )
    if expected != actual:
        sys.exit(f"Missing: {list((expected - actual).elements())}\n"
                 f"Unexpected: {list((actual - expected).elements())}")
    if "ScalafixFailed: LinterError" not in log:
        sys.exit("Expected Scalafix to fail on lint errors.")
    print(f"Verified {expected.total()} exact diagnostics.")


if __name__ == "__main__":
    main()
