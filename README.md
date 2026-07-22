# cloud-itonami-iso3166-zmb

Open ISO 3166 Blueprint for **ZMB**: Zambia.

This repository designs a forkable OSS business for an independent
public-sector market-entry consultant: an already-incorporated operator
(e.g. a `cloud-itonami-cofog-{code}`, `cloud-itonami-isco-{code}`,
`cloud-itonami-unspsc-{segment}` or `cloud-itonami-{ISIC}` blueprint
fork) gets a Compliance Advisor + independent **Market-Entry Compliance
Governor** to navigate public-procurement registration, local business/
tax registration, and local-content rules in Zambia, so the operator
can win and service a government contract without hiring a full in-house
compliance department.

## No robotics premise — digital/data service exemption

Market-entry and procurement-compliance navigation is a pure data/software
service with no physical-domain work (portal registration, document
checklists, regulatory-change monitoring) — the same exemption class as
`cloud-itonami-6310` (HR SaaS replacement) and `cloud-itonami-gtin-*`.
`blueprint.edn` sets `:itonami.blueprint/robotics false` and
`:required-technologies` lists only real capabilities (`:identity`,
`:forms`, `:dmn`, `:bpmn`, `:audit-ledger`), no `:robotics`.

## Core Contract

```text
operator intake + prior filing history
        |
        v
Compliance Advisor -> Market-Entry Compliance Governor -> filing draft, or human sign-off
        |
        v
gated portal registration / filing submission + audit ledger
```

No automated proposal can submit a portal registration or filing the
governor refuses, suppress a compliance record, or claim a legal/tax
conclusion the governor has not cleared. `:filing/submit` is never in any
phase's `:auto` set — it always requires human sign-off (mirrors
`cloud-itonami-M6910`'s `filing-submit-never-auto-at-any-phase`
invariant).

## What this is NOT

- **Not the government of Zambia.** See
  [`docs/business-model.md`](docs/business-model.md) for the boundary with
  `com-etzhayyim-ooyake` (read-only civic mirror), `matsurigoto` (sovereign
  statecraft), `com-etzhayyim-toritsugi` (individual citizen concierge),
  `legal-entity.etzhayyim.com` (read-only data aggregation), and
  `cloud-itonami-M6910` (company incorporation — a different regulatory
  phase this blueprint assumes is already complete).
- **Not legal or tax advice.** Every regulatory claim must cite the
  official source and route final filings to Zambian-licensed counsel
  or a registered agent where the law requires licensed representation.

## Capability layer

Resolves via [`kotoba-lang/iso3166`](https://github.com/kotoba-lang/iso3166)
(ISO 3166 `ZMB`). Required capabilities:

- :identity
- :forms
- :dmn
- :bpmn
- :audit-ledger

See [`docs/business-model.md`](docs/business-model.md) and
[`docs/operator-guide.md`](docs/operator-guide.md).

## License

AGPL-3.0-or-later.

## Market-entry / statute catalogs

Governed public-sector market-entry compliance actor, same architecture
as every `cloud-itonami-iso3166-*` sibling in this fleet:

- `src/marketentry/{facts,governor,phase,sim,operation,registry,store,
  marketentryllm}.cljc` -- the actor. `facts.cljc` cites the Zambia
  Public Procurement Authority (ZPPA, `zppa.org.zm`), whose own
  'About Us' page (fetched directly) confirms the current legal basis
  is the Public Procurement Act, 2020 (Act No. 8 of 2020), which
  repealed and replaced an earlier 2008 Act (ZPPA itself traces back to
  the Zambia National Tender Board Act, Chapter 394 of the Laws of
  Zambia, 1982). ZPPA's own e-GP (Electronic Government Procurement)
  system is live at `eprocure.zppa.org.zm/epps`; ZPPA's own FAQ page
  names the exact supplier-registration evidence checklist ('1. PACRA
  Certificate 2. ZRA TPIN 3. Valid email address 4. A nonrefundable fee
  of K 300.00'). Business/company registration is handled by the
  Patents and Companies Registration Agency (PACRA, `pacra.org.zm`)
  under the Companies Act, 2017 (Act No. 10 of 2017, confirmed via the
  Zambia Legal Information Institute's own filtered legislation
  listing); tax registration is the Zambia Revenue Authority's (ZRA)
  Taxpayer Identification Number (TPIN) scheme. `governor.cljc`'s
  flagship check independently recomputes whether an engagement's own
  claimed citizen-subcontracting compliance matches ZPPA's own
  published Public Procurement Regulations, 2022 (a Statutory
  Instrument, Regulation 20(1)) mandate: a foreign bidder/supplier whose
  declared procurement exceeds the Second Schedule's own Simplified
  Bidding/Simplified Selection threshold (K1,000,000 for goods, works
  and non-consulting services; K600,000 for consulting services) must
  subcontract a minimum of 20% of the procurement's value to citizen
  bidders/suppliers. This is a check shape genuinely different from
  every other iso3166 sibling's (a single-dimension threshold gate
  triggering a flat minimum-percentage set-aside mandate, rather than a
  routing/eligibility lookup) -- see the namespace docstrings for the
  full research trail and honestly-narrowed scope, including facts this
  iteration could NOT verify or deliberately did not model: no
  Zambia-specific representative/director exclusion-extension provision
  was confirmed (an honest gap), no section-level statutory citation
  for the ZRA TPIN scheme itself was independently confirmed (only ZRA's
  own FAQ definition), and the Zambia Development Agency's own 5-tier
  origin-conditional investment-incentive threshold schedule
  (Investment, Trade and Business Development Act, 2022, Act No. 18 of
  2022, Section 30(2)) was independently confirmed but deliberately NOT
  modeled as a governor check this iteration (recorded in the namespace
  docstring as a genuine, non-flagship finding for a future extension).
- `src/statute/facts.cljc` -- general-law catalog: the Companies Act,
  2017 (Act No. 10 of 2017); the Employment Code Act, 2019 (Act No. 3 of
  2019); and the Income Tax Act (Chapter 323 of the Laws of Zambia,
  confirmed via a cross-reference in the Investment, Trade and Business
  Development Act, 2022's own text rather than a direct listing row --
  an honest, disclosed secondary-sourcing path). No Value Added Tax Act
  entry was independently confirmed via this route -- an honest gap, not
  modeled.

Every citation is curl-verified against an official source
(zppa.org.zm, eprocure.zppa.org.zm, pacra.org.zm, info.pacra.org.zm,
zra.org.zm, zambialii.org, zda.org.zm, comesa.int, sadc.int, sacu.int)
and two Statutory Instruments/Acts downloaded and read directly as
PDFs (the Public Procurement Act, 2020 and the Public Procurement
Regulations, 2022) plus the Investment, Trade and Business Development
Act, 2022's full text read directly from ZambiaLII. No source hit a
bot-challenge this iteration. This catalog deliberately does NOT carry
forward unverified reference entries for other jurisdictions from
training-data memory or from a sibling repo -- see `marketentry.facts`'s
namespace docstring.

Zambia is a member of COMESA (confirmed directly from comesa.int's own
'Member States' listing, which links a dedicated `comesa.int/zambia`
page) and SADC (confirmed directly from sadc.int's own 'Member States'
page, which names Zambia and links a dedicated `/member-states/zambia`
page) -- but is NOT a member of the Southern African Customs Union
(SACU): SACU's own site (`sacu.int`) lists exactly five member-state
pages in its own navigation (Botswana, Eswatini, Lesotho, Namibia,
South Africa), and Zambia is absent, confirmed directly rather than
assumed by symmetry with Zambia's Southern African neighbours.

## Culture catalog

Alongside the market-entry / statute catalogs, this repo carries a
**country-level regional-culture catalog** (ADR-2607171400 addendum 2,
`cloud-itonami-municipality-culture-catalog` Wave 1, in
`com-junkawasaki/root`) — national dishes, protected products, beverages,
crafts, festivals and heritage sites for Zambia:

- `src/culture/facts.cljc` — the catalog, source of truth (keyed by
  uppercase ISO3, mirroring `statute.facts`).
- `schema/culture.edn` — DataScript schema.
- `data/culture-tx.edn` — derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis — never fabricate one.
