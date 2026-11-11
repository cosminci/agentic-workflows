---
name: concise-writing
description: Use for prose in guidance, responses, specs, living docs and reviews.
---
# Writing

Obey [ASD-STE100 Issue 9](https://doi.org/10.5281/zenodo.23666006) for all prose. This repository's guidance is part of that scope.

Use the standard's writing rules and dictionary together.

Use `/i-have-adhd` presentation: the answer first, short blocks and clear labels. The reader must find the decision without a long introduction.

## Two checks

| Check | Acceptance |
| --- | --- |
| Signal | Each sentence gives a necessary decision, fact, reason, guarantee or uncertainty |
| Clarity | The reader can find each instruction and its subject without different possible interpretations |

Make sure that the text has signal and clarity. A short sentence without necessary information does not have signal.

## Vocabulary and grammar

- Use general words only with their approved part of speech, meaning and form.
- Use technical nouns and verbs only in the standard's permitted categories.
- Use [domain vocabulary](../principles.md#ownership) for project terms.
- Use an approved general word when it gives the same meaning as a technical verb.
- Use technical terms only for their permitted subject. Use approved general words for general prose.
- Use approved verb forms and simple tenses.
- Use dictionary-approved `-ing` words with their approved part of speech.
- For other `-ing` forms, use only permitted technical nouns or their modifiers.
- Use active voice with a named actor.
- Use passive descriptive text only when the actor is unknown.
- Use the imperative form for instructions.
- Give one instruction for each sentence unless the actions occur at the same time.
- Put a necessary condition before its instruction.
- Give causes, order, exceptions and failure behavior.
- Use full sentences without contractions or missing articles.
- Keep noun groups to at most three words.
- For a longer official term, use the standard's abbreviation or hyphenation method.
- Keep identifiers, commands, contract names and technical terms correct.
- Use periods, not semicolons.
- Use literal language without idioms or new phrasal verbs.
- Use one term for each concept. Make subsequent wording agree with that term.

## Structure and signal

- Start with the answer, decision, finding or proposed change.
- Put evidence after the decision.
- Keep one topic per sentence.
- Keep instructions to at most 20 words and descriptions to at most 25.
- Use the standard's word-count rules for parentheses, quoted text, identifiers, names, hyphens and lists.
- Keep each paragraph or list item to one topic and at most two sentences.
- Use tables for comparisons and numbered steps for procedures.
- Remove sections without different necessary information.
- Remove filler, announcements, tangents, recaps and closing pleasantries.
- Compare scope and concision with the closest approved precedent. Apply current rules when the precedent has defects.
- Include a next action only when necessary.
- Keep evidence, decision authority, safety and uncertainty that changes a decision.
- Keep the work necessary for correctness regardless of report length.
- Obey mandatory templates and Markdown source-wrapping rules.
- Put each Markdown prose paragraph or list item on one physical source line.
- Remove only optional sections without information necessary for a decision.

## Documents

- For procedures, give prerequisites, actions, expected results and recovery in execution order.
- For living docs, use [strategic admission](../context.md#admission-test) before a draft.
- For spec structure, use the [change template](../templates/change-spec.md).
- Use [discovery](../discovery.md) for spec provenance and [execution](../executor.md#understand-and-decide) for other decision categories.
- For review findings, use the [review procedure](../reviewer.md).
- Put checklist labels and evidence in different fields.

## Writing checks

- Examine prose in frontmatter, headings, tables, examples and program messages.
- Remove text without reader value before shorter wording.
- Examine each general word against the official dictionary.
- Examine its meaning, part of speech and form in each sentence.
- Record each technical term's permitted category and use.
- Examine each sentence against the writing rules.
- Compare the result with necessary guarantees and distinctions before acceptance.
- Use word counts and Markdown checks as partial checks, not STE100 proof.
- Until the reviewer completes the full source review, keep the writing gate open.
- Correct all findings before closure.
- If the official standard is not available, get access before a compliance decision.
- Remove synonyms that are not necessary.
- Keep necessary safety or recovery detail regardless of length.
