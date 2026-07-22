# Business Model: Independent Public-Sector Market-Entry & Procurement Compliance Service — Zambia

## Classification

- Repository: `cloud-itonami-iso3166-zmb`
- ISO 3166: `ZMB` (Zambia)
- Activity: public-procurement market-entry and ongoing regulatory-
  compliance navigation for an already-incorporated operator
- Social impact: [:zambian-sme-market-access :public-spend-transparency :cross-border-friction-reduction]

## Customer

- an already-incorporated `cloud-itonami-cofog-{code}` /
  `cloud-itonami-isco-{code}` / `cloud-itonami-unspsc-{segment}` /
  `cloud-itonami-{ISIC}` operator wanting to bid on a Zambian
  public contract
- a foreign SME or civic-tech vendor entering the public sector in
  Zambia for the first time
- a `cloud-itonami-M6910` client that has just completed incorporation and
  now needs public-sector market access

## Offer

- registration walkthrough for the ZPPA e-GP (Electronic Government
  Procurement) system (`eprocure.zppa.org.zm/epps`), operated by the
  Zambia Public Procurement Authority under the Public Procurement Act,
  2020 (Act No. 8 of 2020 -- ZPPA's own site confirms this Act repealed
  and replaced an earlier 2008 Act; ZPPA itself traces back further to
  the Zambia National Tender Board Act, Chapter 394 of the Laws of
  Zambia, 1982). e-GP registration requires a PACRA Certificate and a
  ZRA TPIN (named directly on ZPPA's own FAQ page)
- business/tax registration checklist: a certificate of registration/
  incorporation with the Patents and Companies Registration Agency
  (PACRA, administering the Companies Act, 2017, Act No. 10 of 2017)
  plus a Taxpayer Identification Number (TPIN) with the Zambia Revenue
  Authority (ZRA) -- both named directly as ZPPA's own e-GP supplier
  registration preconditions
- citizen-subcontracting mandate navigation: the Public Procurement
  Regulations, 2022 (Regulation 20(1)) require a foreign bidder/
  supplier whose procurement exceeds the Second Schedule's own
  Simplified Bidding/Simplified Selection threshold (K1,000,000 for
  goods/works/non-consulting services, K600,000 for consulting
  services) to subcontract a minimum of 20% of the procurement's value
  to citizen bidders/suppliers
- ongoing regulatory-change monitoring subscription
- compliance-audit export package for the client's own records

## Revenue

- per-engagement market-entry fee (one-time registration + checklist
  completion)
- recurring regulatory-change monitoring subscription
- compliance-audit export package

## Trust Controls

- any actual portal registration or filing submission requires
  Market-Entry Compliance Governor clearance and always escalates to
  human sign-off (`:filing/submit` is never automated at any phase)
- a false or fabricated regulatory-requirement claim is a HARD hold that
  cannot be overridden by human approval alone — it must be corrected
  against a cited official source first
- this service does **not** provide legal or tax advice; characterization
  and filing on the client's behalf beyond checklist/draft assistance
  routes to Zambian-licensed counsel or a registered agent
- every requirement cites the official portal or regulation, never
  invented

## Boundary with adjacent actors (read before forking)

- **`com-etzhayyim-ooyake`** (etzhayyim/root): read-only civic-wayfinding
  mirror of government structure, non-commercial, barred from acting as
  or for the government (G3 impersonation ban). This blueprint is
  commercial and never claims to be an official channel.
- **`matsurigoto`** (etzhayyim/root): sovereign e-government statecraft —
  literally the government, for etzhayyim's own covenant or an adopting
  nation-state. This blueprint is an independent operator the government
  contracts with or that bids into its procurement — never the
  government.
- **`com-etzhayyim-toritsugi`** (etzhayyim/root): guides a consenting
  INDIVIDUAL citizen through their OWN procedure, non-profit,
  donation-only. This blueprint's client is a business operator, not an
  individual citizen, and it is commercial.
- **`legal-entity.etzhayyim.com`**: read-only aggregated company-registry
  data, no execution. This blueprint executes (gated) registrations.
- **`cloud-itonami-M6910`**: helps a client BECOME a legal entity
  (incorporation, ISIC 6910) — a prior, different regulatory phase
  (company law). This blueprint assumes incorporation is already done and
  handles public-procurement market entry (a different regulatory domain).
- **`cloud-itonami-cofog-{code}`**: a jurisdiction-agnostic operator
  template for ONE public function. This blueprint is the orthogonal
  jurisdiction-specific axis — the two compose (fork a COFOG-function
  blueprint AND this one to operate in Zambia).
