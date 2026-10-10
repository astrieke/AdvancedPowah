# Advanced Powah!

Addon für [Powah! (Rearchitected)](https://www.curseforge.com/minecraft/mc-mods/powah-rearchitected)

## Inhalt

### Neue Stufen: Voltaic, Astral und Quantum

Drei Stufen oberhalb von Nitro. Jede Stufe bringt mit: Kristall, Kristallblock, Kondensator, Batterie,
Energiezelle, Ender-Zelle, Ender-Gate, Energiekabel, Energizing Rod, Furnator, Magmator, Thermogenerator, Solarpanel,
Reaktor, Player Transmitter, Energy Hopper und Energy Discharger.

| | Nitro (Powah) | Voltaic | Astral | Quantum |
|---|---|---|---|---|
| `scale` / `passive_scale` | 2.000 / 100 | 10.000 / 250 | 50.000 / 600 | 200.000 / 1.500 |
| Energiezelle, Batterie, Hopper, Transmitter: Kapazität | 2 Mrd. FE | 10 Mrd. | 50 Mrd. | 200 Mrd. |
| Energiezelle, Ender-Zelle, Hopper, Transmitter: Transfer | 2 Mio. FE/t | 10 Mio. | 50 Mio. | 200 Mio. |
| Kabel, Ender-Gate; Laderate von Hopper und Transmitter | 1 Mio. FE/t | 5 Mio. | 25 Mio. | 100 Mio. |
| Energizing Rod: Transfer / Puffer | 200.000 FE/t / 20 Mio. FE | 1 Mio. / 100 Mio. | 5 Mio. / 500 Mio. | 20 Mio. / 2 Mrd. |
| Furnator, Magmator: Erzeugung | 40.000 FE/t | 200.000 | 1 Mio. | 4 Mio. |
| Solarpanel, Thermogenerator: Erzeugung | 2.000 FE/t | 5.000 | 12.000 | 30.000 |
| Generatoren: Puffer / Transfer | 40 Mio. FE / 160.000 FE/t | 200 Mio. / 800.000 | 1 Mrd. / 4 Mio. | 4 Mrd. / 16 Mio. |
| Reaktor: Erzeugung | 500.000 FE/t | 2,5 Mio. | 12,5 Mio. | 50 Mio. |
| Reaktor: Puffer / Transfer | 500 Mio. FE / 2 Mio. FE/t | 2,5 Mrd. / 10 Mio. | 12,5 Mrd. / 50 Mio. | 50 Mrd. / 200 Mio. |
| Reaktor: Brennstoffverbrauch | 1× | 2× | 4× | 8× |


Die Kristalle entstehen in der Energizing Orb:

- **Voltaic** (120 Mio. FE): Nitro-Kristallblock + Diamantblock + Lapisblock + Netherstern → 8 Kristalle
- **Astral** (600 Mio. FE): Voltaic-Kristallblock + Netheritblock + Drachenatem + Endkristall → 4 Kristalle
- **Quantum** (3 Mrd. FE): Astral-Kristallblock + Netheritblock + Echoscherbe + Netherstern → 4 Kristalle

### Neue Maschinen

- **Mondpanel**: das Gegenstück zum Solarpanel, erzeugt nachts unter freiem Himmel 1.200 FE/t.
- **Hybridpanel**: erzeugt bei Tag und Nacht unter freiem Himmel 2.000 FE/t, unabhängig vom Wetter.
  Gebaut aus einem Nitro-Solarpanel und einem Mondpanel.

Beide werden von unten angeschlossen.

### Kompatibilität

- Alle Blöcke sprechen die normale NeoForge-Energie (FE) und funktionieren mit jeder Mod, die FE kann.
- Die Batterien passen wie Powahs Batterien in Curios-Slots.
- **JEI**: Werkbank-Rezepte lassen sich wie gewohnt nachschlagen und per "+" in die Werkbank legen. Die
  Kristall-Rezepte stehen in Powahs Energizing-Kategorie, die neuen Rods, Magmatoren, Thermogeneratoren und Reaktoren
  sind dort als Arbeitsstationen eingetragen, und Mond- und Hybridpanel haben eine Info-Seite.
- **Tags**: Die Kristalle stehen in `c:gems`, die Kristallblöcke in `c:storage_blocks`, alle Stufen-Blöcke auch als
  Item in Powahs Tags (`powah:energy_cells` usw.). Die eigenen Rezepte nehmen Lapis-, Redstone- und Goldblöcke sowie
  Obsidian über die `c:`-Tags an.
- **Applied Energistics 2** (nur wenn installiert): Die Energizing Orb lädt Certus-Quarz auf (10.000 FE) und stellt
  aus geladenem Certus-Quarz, Redstone und Netherquarz zwei Fluix-Kristalle her (30.000 FE).
- **Mekanism**: Uran, Plutonium-, Polonium- und Antimaterie-Pellets sind Reaktor-Brennstoff, überhitztes Natrium ist
  Magmator-Brennstoff und Wärmequelle, Bioethanol ist Magmator-Brennstoff, das Superheating Element ist Wärmequelle.

---

# Advanced Powah! (Dansk)

Addon til [Powah! (Rearchitected)](https://www.curseforge.com/minecraft/mc-mods/powah-rearchitected)

## Indhold

### Nye niveauer: Voltaic, Astral og Quantum

Tre niveauer over Nitro. Hvert niveau indeholder: krystal, krystalblok, kondensator, batteri,
energicelle, Ender-celle, Ender Gate, energikabel, Energizing Rod, Furnator, Magmator, termogenerator, solpanel,
reaktor, Player Transmitter, Energy Hopper og Energy Discharger.

| | Nitro (Powah) | Voltaic | Astral | Quantum |
|---|---|---|---|---|
| `scale` / `passive_scale` | 2.000 / 100 | 10.000 / 250 | 50.000 / 600 | 200.000 / 1.500 |
| Energicelle, batteri, Hopper, Transmitter: kapacitet | 2 mia. FE | 10 mia. | 50 mia. | 200 mia. |
| Energicelle, Ender-celle, Hopper, Transmitter: overførsel | 2 mio. FE/t | 10 mio. | 50 mio. | 200 mio. |
| Kabel, Ender Gate; opladningshastighed for Hopper og Transmitter | 1 mio. FE/t | 5 mio. | 25 mio. | 100 mio. |
| Energizing Rod: overførsel / buffer | 200.000 FE/t / 20 mio. FE | 1 mio. / 100 mio. | 5 mio. / 500 mio. | 20 mio. / 2 mia. |
| Furnator, Magmator: produktion | 40.000 FE/t | 200.000 | 1 mio. | 4 mio. |
| Solpanel, termogenerator: produktion | 2.000 FE/t | 5.000 | 12.000 | 30.000 |
| Generatorer: buffer / overførsel | 40 mio. FE / 160.000 FE/t | 200 mio. / 800.000 | 1 mia. / 4 mio. | 4 mia. / 16 mio. |
| Reaktor: produktion | 500.000 FE/t | 2,5 mio. | 12,5 mio. | 50 mio. |
| Reaktor: buffer / overførsel | 500 mio. FE / 2 mio. FE/t | 2,5 mia. / 10 mio. | 12,5 mia. / 50 mio. | 50 mia. / 200 mio. |
| Reaktor: brændselsforbrug | 1× | 2× | 4× | 8× |


Krystallerne fremstilles i Energizing Orb:

- **Voltaic** (120 mio. FE): Nitro-krystalblok + diamantblok + lapisblok + netherstjerne → 8 krystaller
- **Astral** (600 mio. FE): Voltaic-krystalblok + netheritblok + drageånde + endekrystal → 4 krystaller
- **Quantum** (3 mia. FE): Astral-krystalblok + netheritblok + ekkoskår + netherstjerne → 4 krystaller

### Nye maskiner

- **Månepanel**: modstykket til solpanelet, producerer 1.200 FE/t om natten under åben himmel.
- **Hybridpanel**: producerer 2.000 FE/t både dag og nat under åben himmel, uafhængigt af vejret.
  Bygges af et Nitro-solpanel og et månepanel.

Begge tilsluttes nedefra.

### Kompatibilitet

- Alle blokke bruger den almindelige NeoForge-energi (FE) og fungerer med alle mods, der understøtter FE.
- Batterierne passer i Curios-pladser ligesom Powahs egne batterier.
- **JEI**: Opskrifter til arbejdsbordet kan slås op som normalt og lægges i arbejdsbordet med "+".
  Krystalopskrifterne står i Powahs Energizing-kategori, de nye Rods, Magmatorer, termogeneratorer og reaktorer
  er registreret dér som arbejdsstationer, og måne- og hybridpanelet har en infoside.
- **Tags**: Krystallerne står i `c:gems`, krystalblokkene i `c:storage_blocks`, og alle niveau-blokke findes også som
  item i Powahs tags (`powah:energy_cells` osv.). De egne opskrifter accepterer lapis-, redstone- og guldblokke samt
  obsidian via `c:`-tags.
- **Applied Energistics 2** (kun hvis installeret): Energizing Orb oplader certuskvarts (10.000 FE) og fremstiller
  to Fluix-krystaller af opladet certuskvarts, redstone og netherkvarts (30.000 FE).
- **Mekanism**: Uran samt plutonium-, polonium- og antistof-pellets er reaktorbrændsel, overophedet natrium er
  Magmator-brændsel og varmekilde, bioethanol er Magmator-brændsel, og Superheating Element er varmekilde.
