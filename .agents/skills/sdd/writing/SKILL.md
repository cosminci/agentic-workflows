---
name: concise-writing
description: Use when writing agent responses, proposals, documentation or reviews.
---
# Concise writing

STE-informed guidance, based on [ASD's overview of Simplified Technical English](https://www.asd-europe.org/standards-specifications/simplified-technical-english/): active voice, short sentences, logical structure and consistent meanings. This skill does not implement the complete ASD-STE100 rules or dictionary and does not establish STE compliance.

- Lead with the answer, decision, finding or concrete proposal. Put supporting evidence after it.
- Use the project's established terms under [domain vocabulary](../principles.md#ownership); prefer plain, literal language for explanations.
- Use one term for each concept and keep its meaning consistent. Preserve authoritative project terms, identifiers, commands and contractual wording; simpler language must not rename or weaken them.
- Use active voice and name the responsible actor. Make pronoun references explicit when more than one actor or value could fit.
- Write short sentences with one main idea. Split dense clauses without losing conditions, exceptions, ordering or causality; word counts do not establish clarity.
- State each fact once, at its owning section. Keep only sections that add distinct, necessary information.
- Use short paragraphs; use a table for comparisons and numbered steps only for ordered actions. Present the decision without making the reader reconstruct it from background.
- Remove announcements, filler, tangents, repeated state, recaps and closing pleasantries. End when the answer is complete; include a next action only when needed.
- Before sending, remove sentences that add no decision, evidence or necessary explanation. Preserve requirements, guarantees, failure and edge cases, decision provenance, safety information and meaningful uncertainty.
- Brevity changes presentation, not investigation or review coverage. Follow required templates and existing Markdown/source-wrapping rules; omit only optional sections without distinct content.

## Documents

- Procedures and runbooks: use direct instructions in execution order. Put prerequisites and conditions before the affected action; distinguish actions, expected results and failure/recovery instructions. Preserve necessary safety information.
- Strategic/context documents: name responsibilities, interactions and guarantees in short explanations. Apply [context ownership](../context.md) before editing; clear wording does not justify misplaced implementation detail.
- Specs: name the actor, relevant condition, observable outcome and failure behavior. Keep required behavior, proposed choices and unresolved decisions distinct; preserve required contracts and model evolution.

## Check

- Read the result for ambiguous terms, actors, references and conditions. Verify that shortening preserved every required distinction and that each fact belongs in the document.
- Reject a concise but incomplete spec, a clear feature walkthrough in strategic docs or an invented synonym for an existing concept. Accept precise project terminology and necessary safety or recovery detail even when they need a longer explanation.
