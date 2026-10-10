#!/usr/bin/env python3

import colorsys
import json
import shutil
import sys
from pathlib import Path

from PIL import Image

MOD = "advancedpowah"
ROOT = Path(__file__).resolve().parent.parent
RES = ROOT / "src/main/resources"
ASSETS = RES / "assets" / MOD
DATA = RES / "data"

if len(sys.argv) < 2:
    sys.exit(__doc__)
POWAH_TEX = Path(sys.argv[1]) / "src/main/resources/assets/powah/textures"

TIERS = {
    "voltaic": (0.615, 1.15, 1.05, "Voltaic", "Voltaic"),
    "astral": (0.795, 0.95, 1.15, "Astral", "Astral"),
    "quantum": (0.905, 0.90, 1.25, "Quantum", "Quantum"),
}

PREVIOUS = {"voltaic": ("powah", "nitro"), "astral": (MOD, "voltaic"), "quantum": (MOD, "astral")}

CRYSTAL_BLOCK_HEAT = {"voltaic": 3500, "astral": 4500, "quantum": 5500}

TIER_ORDER = ("voltaic", "astral", "quantum")

FIXED_KEYS = ("block.lunar_panel", "block.hybrid_panel", "info.lunar_panel", "info.hybrid_panel", "info.idle",
              "jei.lunar_panel", "jei.hybrid_panel")

TIERED_KEYS = ("item.crystal_{tier}", "item.capacitor_{tier}", "item.battery_{tier}", "block.{tier}_crystal_block",
               "block.energy_cell_{tier}", "block.energy_cable_{tier}", "block.solar_panel_{tier}",
               "block.thermo_generator_{tier}", "block.furnator_{tier}", "block.magmator_{tier}",
               "block.ender_cell_{tier}", "block.ender_gate_{tier}", "block.energizing_rod_{tier}",
               "block.reactor_{tier}", "block.player_transmitter_{tier}", "block.energy_hopper_{tier}",
               "block.energy_discharger_{tier}")

EXTRA_LANGS = {
    "ru_ru": {
        "tiers": ("Вольтаик", "Астрал", "Квантум"),
        "fixed": ("Лунная панель", "Гибридная панель",
                  "Вырабатывает энергию ночью под открытым небом",
                  "Вырабатывает энергию днём и ночью под открытым небом",
                  "Простаивает",
                  "Вырабатывает энергию ночью, пока видит небо. Подключается снизу, как солнечная панель.",
                  "Вырабатывает энергию днём и ночью, пока видит небо. Погода не влияет. Подключается снизу, как солнечная панель."),
        "tiered": ("{t}-кристалл", "{t}-конденсатор", "Аккумулятор ({t})", "Блок {t}-кристаллов",
                   "Энергоячейка ({t})", "Энергокабель ({t})", "Солнечная панель ({t})", "Термогенератор ({t})",
                   "Углегенератор ({t})", "Магмогенератор ({t})", "Эндер-ячейка ({t})", "Эндер-шлюз ({t})",
                   "Зарядоотвод ({t})", "Реактор ({t})", "Беспроводной зарядник ({t})", "Энерговоронка ({t})",
                   "Энергоразрядник ({t})"),
    },
    "pt_br": {
        "tiers": ("Voltaic", "Astral", "Quantum"),
        "fixed": ("Painel Lunar", "Painel Híbrido",
                  "Gera energia à noite sob céu aberto",
                  "Gera energia de dia e de noite sob céu aberto",
                  "Inativo",
                  "Gera energia à noite enquanto consegue ver o céu. Conecte por baixo, como um Painel Solar.",
                  "Gera energia de dia e de noite enquanto consegue ver o céu. O clima não importa. Conecte por baixo, como um Painel Solar."),
        "tiered": ("Cristal {t}", "Capacitor {t}", "Bateria ({t})", "Bloco de Cristal {t}",
                   "Célula de Energia ({t})", "Cabo de Energia ({t})", "Painel Solar ({t})", "Gerador Térmico ({t})",
                   "Fornador ({t})", "Magmador ({t})", "Célula do Ender ({t})", "Portão do Ender ({t})",
                   "Haste Energizante ({t})", "Reator ({t})", "Transmissor de Jogador ({t})", "Funil de Energia ({t})",
                   "Descarregador de Energia ({t})"),
    },
    "es_es": {
        "tiers": ("Voltaic", "Astral", "Quantum"),
        "fixed": ("Panel lunar", "Panel híbrido",
                  "Genera energía de noche a cielo abierto",
                  "Genera energía de día y de noche a cielo abierto",
                  "Inactivo",
                  "Genera energía de noche mientras pueda ver el cielo. Se conecta por debajo, como un panel solar.",
                  "Genera energía de día y de noche mientras pueda ver el cielo. El clima no influye. Se conecta por debajo, como un panel solar."),
        "tiered": ("Cristal {t}", "Condensador {t}", "Batería ({t})", "Bloque de cristal {t}",
                   "Celda de energía ({t})", "Cable de energía ({t})", "Panel solar ({t})", "Generador térmico ({t})",
                   "Furnator ({t})", "Magmator ({t})", "Celda de Ender ({t})", "Puerta de Ender ({t})",
                   "Vara energizante ({t})", "Reactor ({t})", "Transmisor de jugador ({t})", "Tolva de energía ({t})",
                   "Descargador de energía ({t})"),
    },
    "fr_fr": {
        "tiers": ("Voltaic", "Astral", "Quantum"),
        "fixed": ("Panneau lunaire", "Panneau hybride",
                  "Produit de l'énergie la nuit à ciel ouvert",
                  "Produit de l'énergie de jour comme de nuit à ciel ouvert",
                  "Inactif",
                  "Produit de l'énergie la nuit tant qu'il voit le ciel. Se raccorde par le dessous, comme un panneau solaire.",
                  "Produit de l'énergie de jour comme de nuit tant qu'il voit le ciel. La météo n'a aucune influence. Se raccorde par le dessous, comme un panneau solaire."),
        "tiered": ("Cristal {t}", "Condensateur {t}", "Pile ({t})", "Bloc de cristal {t}",
                   "Cellule d'énergie ({t})", "Câble d'énergie ({t})", "Panneau solaire ({t})", "Thermogénérateur ({t})",
                   "Chaudière ({t})", "Magmateur ({t})", "Cellule de l'End ({t})", "Porte de l'End ({t})",
                   "Tige énergisante ({t})", "Réacteur ({t})", "Transmetteur joueur ({t})", "Entonnoir à énergie ({t})",
                   "Déchargeur d'énergie ({t})"),
    },
    "ja_jp": {
        "tiers": ("ボルタイック", "アストラル", "クアンタム"),
        "fixed": ("ルナパネル", "ハイブリッドパネル",
                  "夜間、空が見える場所で発電する",
                  "昼も夜も、空が見える場所で発電する",
                  "待機中",
                  "空が見えている間、夜に発電します。ソーラーパネルと同じく下面から接続します。",
                  "空が見えている間、昼も夜も発電します。天候の影響は受けません。ソーラーパネルと同じく下面から接続します。"),
        "tiered": ("{t}クリスタル", "{t}キャパシタ", "バッテリー ({t})", "{t}クリスタルブロック",
                   "エネルギーセル ({t})", "エネルギーケーブル ({t})", "ソーラーパネル ({t})", "熱発電機 ({t})",
                   "ファーネター ({t})", "マグマター ({t})", "エンダーセル ({t})", "エンダーゲート ({t})",
                   "エナジャイジングロッド ({t})", "リアクター ({t})", "プレイヤートランスミッター ({t})", "エネルギーホッパー ({t})",
                   "エネルギーディスチャージャー ({t})"),
    },
    "ko_kr": {
        "tiers": ("볼타익", "아스트랄", "퀀텀"),
        "fixed": ("루나 패널", "하이브리드 패널",
                  "밤에 하늘이 보이는 곳에서 발전합니다",
                  "낮과 밤 모두 하늘이 보이는 곳에서 발전합니다",
                  "대기 중",
                  "하늘이 보이는 동안 밤에 에너지를 생산합니다. 태양광 패널처럼 아래쪽에서 연결합니다.",
                  "하늘이 보이는 동안 낮과 밤 모두 에너지를 생산합니다. 날씨의 영향을 받지 않습니다. 태양광 패널처럼 아래쪽에서 연결합니다."),
        "tiered": ("{t} 크리스탈", "{t} 축전기", "배터리 ({t})", "{t} 크리스탈 블록",
                   "에너지 셀 ({t})", "에너지 케이블 ({t})", "태양광 패널 ({t})", "열 발전기 ({t})",
                   "퍼네이터 ({t})", "마그메이터 ({t})", "엔더 셀 ({t})", "엔더 게이트 ({t})",
                   "에너자이징 로드 ({t})", "반응로 ({t})", "플레이어 송신기 ({t})", "에너지 호퍼 ({t})",
                   "에너지 방전기 ({t})"),
    },
    "da_dk": {
        "tiers": ("Voltaic", "Astral", "Quantum"),
        "fixed": ("Månepanel", "Hybridpanel",
                  "Producerer energi om natten under åben himmel",
                  "Producerer energi både dag og nat under åben himmel",
                  "Inaktiv",
                  "Producerer energi om natten, så længe det kan se himlen. Tilsluttes nedefra ligesom et solpanel.",
                  "Producerer energi både dag og nat, så længe det kan se himlen. Vejret har ingen betydning. Tilsluttes nedefra ligesom et solpanel."),
        "tiered": ("{t}-krystal", "{t}-kondensator", "Batteri ({t})", "{t}-krystalblok",
                   "Energicelle ({t})", "Energikabel ({t})", "Solpanel ({t})", "Termogenerator ({t})",
                   "Furnator ({t})", "Magmator ({t})", "Ender-celle ({t})", "Ender Gate ({t})",
                   "Energizing Rod ({t})", "Reaktor ({t})", "Player Transmitter ({t})", "Energy Hopper ({t})",
                   "Energy Discharger ({t})"),
    },
}

def write_json(path: Path, obj):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(obj, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")

def recolor(src: Path, dst: Path, hue: float, sat_mul: float, val_mul: float):

    im = Image.open(src).convert("RGBA")
    px = im.load()
    for y in range(im.height):
        for x in range(im.width):
            r, g, b, a = px[x, y]
            if a == 0:
                continue
            h, s, v = colorsys.rgb_to_hsv(r / 255, g / 255, b / 255)
            is_red = (h < 0.09 or h > 0.91) and s > 0.12
            if is_red:
                nr, ng, nb = colorsys.hsv_to_rgb(hue, min(1.0, s * sat_mul), min(1.0, v * val_mul))
                px[x, y] = (round(nr * 255), round(ng * 255), round(nb * 255), a)
    dst.parent.mkdir(parents=True, exist_ok=True)
    im.save(dst)

def tex(path: str) -> Image.Image:
    return Image.open(POWAH_TEX / path).convert("RGBA")

def save(im: Image.Image, name: str):
    out = ASSETS / "textures" / name
    out.parent.mkdir(parents=True, exist_ok=True)
    im.save(out)

def draw(im: Image.Image, rows, palette, ox=0, oy=0):

    px = im.load()
    for y, row in enumerate(rows):
        for x, ch in enumerate(row):
            if ch != ".":
                px[ox + x, oy + y] = palette[ch]

def opaque_on(base: Image.Image, overlay: Image.Image) -> Image.Image:
    out = base.copy()
    out.alpha_composite(overlay)
    return out

def tier_textures():
    for tier, (hue, sat, val, _, _) in TIERS.items():
        jobs = {
            "block/energy_cell_nitro.png": f"block/energy_cell_{tier}.png",
            "block/energy_cable_nitro.png": f"block/energy_cable_{tier}.png",
            "block/solar_panel_nitro_frame.png": f"block/solar_panel_{tier}_frame.png",
            "block/thermo_generator_nitro_core.png": f"block/thermo_generator_{tier}_core.png",
            "block/thermo_generator_nitro_top.png": f"block/thermo_generator_{tier}_top.png",
            "block/nitro_ov.png": f"block/{tier}_ov.png",
            "block/ender_cell_nitro.png": f"block/ender_cell_{tier}.png",
            "block/energizing_rod_nitro_gem.png": f"block/energizing_rod_{tier}_gem.png",
            "block/player_transmitter_nitro_var.png": f"block/player_transmitter_{tier}_var.png",

            "model/tile/reactor_block_nitro.png": f"model/tile/reactor_block_{tier}.png",
            "model/tile/reactor_nitro.png": f"model/tile/reactor_{tier}.png",
            "model/tile/energy_cable_nitro_all.png": f"model/tile/energy_cable_{tier}_all.png",
            "model/tile/energy_cable_nitro_in.png": f"model/tile/energy_cable_{tier}_in.png",
            "model/tile/energy_cable_nitro_out.png": f"model/tile/energy_cable_{tier}_out.png",
            "block/nitro_crystal_block.png": f"block/{tier}_crystal_block.png",
            "item/crystal_nitro.png": f"item/crystal_{tier}.png",
            "item/capacitor_nitro.png": f"item/capacitor_{tier}.png",
            "item/battery_nitro.png": f"item/battery_{tier}.png",
        }
        for src, dst in jobs.items():
            recolor(POWAH_TEX / src, ASSETS / "textures" / dst, hue, sat, val)

        shutil.copy(POWAH_TEX / "block/nitro_crystal_block.png.mcmeta", ASSETS / "textures" / f"block/{tier}_crystal_block.png.mcmeta")
        shutil.copy(POWAH_TEX / "item/crystal_nitro.png.mcmeta", ASSETS / "textures" / f"item/crystal_{tier}.png.mcmeta")
        shutil.copy(POWAH_TEX / "block/ender_cell_nitro.png.mcmeta", ASSETS / "textures" / f"block/ender_cell_{tier}.png.mcmeta")

def machine_textures():

    panel = tex("block/solar_panel_top.png")
    px = panel.load()

    for y in range(16):
        for x in range(16):
            r, g, b, a = px[x, y]
            _, _, v = colorsys.rgb_to_hsv(r / 255, g / 255, b / 255)
            nr, ng, nb = colorsys.hsv_to_rgb(0.64, 0.75, min(1.0, v + 0.04))
            px[x, y] = (round(nr * 255), round(ng * 255), round(nb * 255), 255)

    for y in range(16):
        for x in range(16):
            d = ((x - 7.5) ** 2 + (y - 7.5) ** 2) ** 0.5
            if d <= 5.2:
                lit = x <= 7
                if d > 4.3:
                    px[x, y] = (190, 192, 190, 255) if lit else (34, 42, 80, 255)
                else:
                    px[x, y] = (236, 234, 212, 255) if lit else (44, 54, 98, 255)

    for x, y in ((4, 6), (5, 9), (3, 8)):
        px[x, y] = (200, 200, 190, 255)
    save(panel, "block/lunar_panel_top.png")

    recolor(POWAH_TEX / "block/solar_panel_nitro_frame.png", ASSETS / "textures/block/lunar_panel_frame.png", 0.60, 0.40, 1.6)

    hybrid = tex("block/solar_panel_top.png")
    hx = hybrid.load()
    for y in range(16):
        for x in range(16):
            r, g, b, a = hx[x, y]
            _, _, v = colorsys.rgb_to_hsv(r / 255, g / 255, b / 255)
            if x <= 7:
                nr, ng, nb = colorsys.hsv_to_rgb(0.57, 0.62, min(1.0, v + 0.22))
            else:
                nr, ng, nb = colorsys.hsv_to_rgb(0.66, 0.78, min(1.0, v + 0.02))
            hx[x, y] = (round(nr * 255), round(ng * 255), round(nb * 255), 255)

    for y in range(16):
        for x in range(16):
            d = ((x - 7.5) ** 2 + (y - 7.5) ** 2) ** 0.5
            if d <= 4.3:
                if x <= 7:
                    hx[x, y] = (255, 196, 54, 255) if d > 3.3 else (255, 232, 110, 255)
                else:
                    hx[x, y] = (176, 180, 196, 255) if d > 3.3 else (232, 232, 220, 255)

    for x, y in ((9, 6), (10, 9), (9, 8)):
        hx[x, y] = (196, 198, 200, 255)
    save(hybrid, "block/hybrid_panel_top.png")

    recolor(POWAH_TEX / "block/solar_panel_nitro_frame.png", ASSETS / "textures/block/hybrid_panel_frame.png", 0.12, 0.55, 1.5)

def block_model(name, parent, textures):
    write_json(ASSETS / "models/block" / f"{name}.json", {"parent": parent, "textures": textures})

def item_model(name, parent, textures):
    write_json(ASSETS / "models/item" / f"{name}.json", {"parent": parent, "textures": textures})

def blockstate(name, obj):
    write_json(ASSETS / "blockstates" / f"{name}.json", obj)

def item_def(name, model):
    write_json(ASSETS / "items" / f"{name}.json", {"model": {"type": "minecraft:model", "model": model}})

def simple_state(name):
    blockstate(name, {"variants": {"": {"model": f"{MOD}:block/{name}"}}})

def models():
    for tier in TIERS:

        for item in (f"crystal_{tier}", f"capacitor_{tier}", f"battery_{tier}"):
            item_model(item, "minecraft:item/generated", {"layer0": f"{MOD}:item/{item}"})
            item_def(item, f"{MOD}:item/{item}")

        name = f"{tier}_crystal_block"
        block_model(name, "minecraft:block/cube_all", {"all": f"{MOD}:block/{name}"})
        simple_state(name)
        item_def(name, f"{MOD}:block/{name}")

        name = f"energy_cell_{tier}"
        block_model(name, "powah:block/energy_cell", {"all": f"{MOD}:block/{name}"})
        simple_state(name)
        item_def(name, f"{MOD}:block/{name}")

        name = f"energy_cable_{tier}"
        block_model(name, "powah:block/cable", {"all": f"{MOD}:block/{name}"})
        block_model(f"{name}_multipart", "powah:block/cable_multipart", {"all": f"{MOD}:block/{name}"})
        part = f"{MOD}:block/{name}_multipart"
        blockstate(name, {"multipart": [
            {"apply": {"model": f"{MOD}:block/{name}"}},
            {"apply": {"model": part}, "when": {"north": "true"}},
            {"apply": {"model": part, "y": 90}, "when": {"east": "true"}},
            {"apply": {"model": part, "x": 180}, "when": {"south": "true"}},
            {"apply": {"model": part, "y": 270}, "when": {"west": "true"}},
            {"apply": {"model": part, "x": 270}, "when": {"up": "true"}},
            {"apply": {"model": part, "x": 90}, "when": {"down": "true"}},
        ]})
        item_def(name, f"{MOD}:block/{name}")

        name = f"solar_panel_{tier}"
        frame = f"{MOD}:block/{name}_frame"
        panel_tex = {"frame": frame, "panel": "powah:block/solar_panel_top"}
        block_model(name, "powah:block/solar_panel", panel_tex)
        block_model(f"{name}_frame", "powah:block/solar_panel_frame", {"frame": frame})
        item_model(name, "powah:item/solar_panel", panel_tex)
        blockstate(name, {"multipart": [
            {"apply": {"model": f"{MOD}:block/{name}"}},
            {"apply": {"model": frame}, "when": {"north": "true"}},
            {"apply": {"model": frame, "y": 90}, "when": {"east": "true"}},
            {"apply": {"model": frame, "y": 180}, "when": {"south": "true"}},
            {"apply": {"model": frame, "y": 270}, "when": {"west": "true"}},
        ]})
        item_def(name, f"{MOD}:item/{name}")

        name = f"thermo_generator_{tier}"
        block_model(name, "powah:block/thermo_generator", {
            "core": f"{MOD}:block/{name}_core",
            "heater": "powah:block/thermo_generator_heater",
            "top": f"{MOD}:block/{name}_top",
        })
        simple_state(name)
        item_def(name, f"{MOD}:block/{name}")

        name = f"furnator_{tier}"
        block_model(name, "powah:block/furnator", {"ov": f"{MOD}:block/{tier}_ov"})
        blockstate(name, {"variants": {
            "facing=north": {"model": f"{MOD}:block/{name}"},
            "facing=east": {"model": f"{MOD}:block/{name}", "y": 90},
            "facing=south": {"model": f"{MOD}:block/{name}", "y": 180},
            "facing=west": {"model": f"{MOD}:block/{name}", "y": 270},
        }})
        item_def(name, f"{MOD}:block/{name}")

        name = f"magmator_{tier}"
        block_model(name, "powah:block/magmator", {"ov": f"{MOD}:block/{tier}_ov"})
        block_model(f"{name}_on", "powah:block/magmator", {"face": "powah:block/magmator_face_lit", "ov": f"{MOD}:block/{tier}_ov"})
        variants = {}
        for facing, rot in (("north", 0), ("east", 90), ("south", 180), ("west", 270)):
            for lit, model in (("false", name), ("true", f"{name}_on")):
                entry = {"model": f"{MOD}:block/{model}"}
                if rot:
                    entry["y"] = rot
                variants[f"facing={facing},lit={lit}"] = entry
        blockstate(name, {"variants": variants})
        item_def(name, f"{MOD}:block/{name}")

        name = f"ender_cell_{tier}"
        block_model(name, "powah:block/ender_cell", {"all": f"{MOD}:block/{name}"})
        simple_state(name)
        item_def(name, f"{MOD}:block/{name}")

        name = f"ender_gate_{tier}"
        block_model(name, "powah:block/ender_gate", {"ov": f"{MOD}:block/{tier}_ov"})
        model = f"{MOD}:block/{name}"
        blockstate(name, {"variants": {
            "facing=down": {"model": model, "x": 90},
            "facing=east": {"model": model, "y": 90},
            "facing=north": {"model": model},
            "facing=south": {"model": model, "y": 180},
            "facing=up": {"model": model, "x": 270},
            "facing=west": {"model": model, "y": 270},
        }})
        item_def(name, model)

        name = f"energizing_rod_{tier}"
        block_model(name, "powah:block/energizing_rod", {"gem": f"{MOD}:block/{name}_gem"})
        model = f"{MOD}:block/{name}"
        blockstate(name, {"variants": {
            "facing=down": {"model": model},
            "facing=east": {"model": model, "x": 90, "y": 270},
            "facing=north": {"model": model, "x": 90, "y": 180},
            "facing=south": {"model": model, "x": 90},
            "facing=up": {"model": model, "x": 180},
            "facing=west": {"model": model, "x": 90, "y": 90},
        }})
        item_def(name, model)

        name = f"player_transmitter_{tier}"
        var = {"var": f"{MOD}:block/{name}_var"}
        block_model(name, "powah:block/player_transmitter", var)
        block_model(f"{name}_top", "powah:block/player_transmitter_top", var)
        item_model(name, "powah:item/player_transmitter", var)
        blockstate(name, {"variants": {
            "top=false": {"model": f"{MOD}:block/{name}"},
            "top=true": {"model": f"{MOD}:block/{name}_top"},
        }})
        item_def(name, f"{MOD}:item/{name}")

        name = f"reactor_{tier}"
        block_model(name, "minecraft:block/block", {"particle": "powah:block/furnator_face"})
        simple_state(name)
        write_json(ASSETS / "items" / f"{name}.json", {"model": {
            "type": "minecraft:special",
            "base": f"{MOD}:block/{name}",
            "model": {"type": f"{MOD}:reactor", "tier": tier},
        }})

        name = f"energy_hopper_{tier}"
        block_model(name, "powah:block/energy_hopper", {"ov": f"{MOD}:block/{tier}_ov"})
        model = f"{MOD}:block/{name}"
        blockstate(name, {"variants": {
            "facing=down": {"model": model, "x": 90},
            "facing=east": {"model": model, "y": 90},
            "facing=north": {"model": model},
            "facing=south": {"model": model, "y": 180},
            "facing=up": {"model": model, "x": 270},
            "facing=west": {"model": model, "y": 270},
        }})
        item_def(name, model)

        name = f"energy_discharger_{tier}"
        block_model(name, "powah:block/energy_discharger", {"ov": f"{MOD}:block/{tier}_ov"})
        model = f"{MOD}:block/{name}"
        blockstate(name, {"variants": {
            "facing=north": {"model": model},
            "facing=east": {"model": model, "y": 90},
            "facing=south": {"model": model, "y": 180},
            "facing=west": {"model": model, "y": 270},
        }})
        item_def(name, model)

    lunar_tex = {"frame": f"{MOD}:block/lunar_panel_frame", "panel": f"{MOD}:block/lunar_panel_top"}
    block_model("lunar_panel", "powah:item/solar_panel", lunar_tex)
    simple_state("lunar_panel")
    item_def("lunar_panel", f"{MOD}:block/lunar_panel")

    hybrid_tex = {"frame": f"{MOD}:block/hybrid_panel_frame", "panel": f"{MOD}:block/hybrid_panel_top"}
    block_model("hybrid_panel", "powah:item/solar_panel", hybrid_tex)
    simple_state("hybrid_panel")
    item_def("hybrid_panel", f"{MOD}:block/hybrid_panel")

def lang():
    en = {
        "itemGroup.advancedpowah.tab": "Advanced Powah!",
        "block.advancedpowah.lunar_panel": "Lunar Panel",
        "block.advancedpowah.hybrid_panel": "Hybrid Panel",
        "info.advancedpowah.hybrid_panel": "Generates by day and by night under open sky",
        "jei.advancedpowah.hybrid_panel": "Generates energy by day and by night while it can see the sky. Weather does not matter. Connect it from below, like a Solar Panel.",
        "info.advancedpowah.lunar_panel": "Generates at night under open sky",
        "info.advancedpowah.idle": "Idle",
        "jei.advancedpowah.lunar_panel": "Generates energy at night while it can see the sky. Connect it from below, like a Solar Panel.",
    }
    de = {
        "itemGroup.advancedpowah.tab": "Advanced Powah!",
        "block.advancedpowah.lunar_panel": "Mondpanel",
        "block.advancedpowah.hybrid_panel": "Hybridpanel",
        "info.advancedpowah.hybrid_panel": "Erzeugt bei Tag und Nacht unter freiem Himmel Energie",
        "jei.advancedpowah.hybrid_panel": "Erzeugt bei Tag und Nacht Energie, solange es den Himmel sieht. Das Wetter spielt keine Rolle. Anschluss von unten, wie beim Solarpanel.",
        "info.advancedpowah.lunar_panel": "Erzeugt nachts unter freiem Himmel Energie",
        "info.advancedpowah.idle": "Inaktiv",
        "jei.advancedpowah.lunar_panel": "Erzeugt nachts Energie, solange es den Himmel sieht. Anschluss von unten, wie beim Solarpanel.",
    }
    for tier, (_, _, _, name_en, name_de) in TIERS.items():
        en[f"item.{MOD}.crystal_{tier}"] = f"{name_en} Crystal"
        en[f"item.{MOD}.capacitor_{tier}"] = f"{name_en} Capacitor"
        en[f"item.{MOD}.battery_{tier}"] = f"Battery ({name_en})"
        en[f"block.{MOD}.{tier}_crystal_block"] = f"Block Of {name_en} Crystal"
        en[f"block.{MOD}.energy_cell_{tier}"] = f"Energy Cell ({name_en})"
        en[f"block.{MOD}.energy_cable_{tier}"] = f"Energy Cable ({name_en})"
        en[f"block.{MOD}.solar_panel_{tier}"] = f"Solar Panel ({name_en})"
        en[f"block.{MOD}.thermo_generator_{tier}"] = f"Thermo Generator ({name_en})"
        en[f"block.{MOD}.furnator_{tier}"] = f"Furnator ({name_en})"
        en[f"block.{MOD}.magmator_{tier}"] = f"Magmator ({name_en})"
        en[f"block.{MOD}.ender_cell_{tier}"] = f"Ender Cell ({name_en})"
        en[f"block.{MOD}.ender_gate_{tier}"] = f"Ender Gate ({name_en})"
        en[f"block.{MOD}.energizing_rod_{tier}"] = f"Energizing Rod ({name_en})"
        en[f"block.{MOD}.reactor_{tier}"] = f"Reactor ({name_en})"
        en[f"block.{MOD}.player_transmitter_{tier}"] = f"Player Transmitter ({name_en})"
        en[f"block.{MOD}.energy_hopper_{tier}"] = f"Energy Hopper ({name_en})"
        en[f"block.{MOD}.energy_discharger_{tier}"] = f"Energy Discharger ({name_en})"

        de[f"item.{MOD}.crystal_{tier}"] = f"{name_de}-Kristall"
        de[f"item.{MOD}.capacitor_{tier}"] = f"{name_de}-Kondensator"
        de[f"item.{MOD}.battery_{tier}"] = f"Batterie ({name_de})"
        de[f"block.{MOD}.{tier}_crystal_block"] = f"{name_de}-Kristallblock"
        de[f"block.{MOD}.energy_cell_{tier}"] = f"Energiezelle ({name_de})"
        de[f"block.{MOD}.energy_cable_{tier}"] = f"Energiekabel ({name_de})"
        de[f"block.{MOD}.solar_panel_{tier}"] = f"Solarpanel ({name_de})"
        de[f"block.{MOD}.thermo_generator_{tier}"] = f"Thermogenerator ({name_de})"
        de[f"block.{MOD}.furnator_{tier}"] = f"Furnator ({name_de})"
        de[f"block.{MOD}.magmator_{tier}"] = f"Magmator ({name_de})"
        de[f"block.{MOD}.ender_cell_{tier}"] = f"Ender-Zelle ({name_de})"
        de[f"block.{MOD}.ender_gate_{tier}"] = f"Ender-Gate ({name_de})"
        de[f"block.{MOD}.energizing_rod_{tier}"] = f"Energizing Rod ({name_de})"
        de[f"block.{MOD}.reactor_{tier}"] = f"Reaktor ({name_de})"
        de[f"block.{MOD}.player_transmitter_{tier}"] = f"Player Transmitter ({name_de})"
        de[f"block.{MOD}.energy_hopper_{tier}"] = f"Energy Hopper ({name_de})"
        de[f"block.{MOD}.energy_discharger_{tier}"] = f"Energy Discharger ({name_de})"
    write_json(ASSETS / "lang/en_us.json", dict(sorted(en.items())))
    write_json(ASSETS / "lang/de_de.json", dict(sorted(de.items())))

    for code, entry in EXTRA_LANGS.items():
        out = {"itemGroup.advancedpowah.tab": "Advanced Powah!"}
        for key, text in zip(FIXED_KEYS, entry["fixed"], strict=True):
            kind, name = key.split(".", 1)
            out[f"{kind}.{MOD}.{name}"] = text
        for tier, tier_name in zip(TIER_ORDER, entry["tiers"], strict=True):
            for key, text in zip(TIERED_KEYS, entry["tiered"], strict=True):
                kind, name = key.split(".", 1)
                out[f"{kind}.{MOD}.{name.format(tier=tier)}"] = text.format(t=tier_name)
        write_json(ASSETS / f"lang/{code}.json", dict(sorted(out.items())))

def shaped(name, pattern, key, result, count=1):
    res = {"id": result}
    if count > 1:
        res = {"count": count, "id": result}
    write_json(DATA / MOD / "recipe/crafting" / f"{name}.json", {
        "type": "minecraft:crafting_shaped",
        "category": "misc",
        "key": key,
        "pattern": pattern,
        "result": res,
    })

def shapeless(name, ingredients, result, count=1):
    res = {"id": result}
    if count > 1:
        res = {"count": count, "id": result}
    write_json(DATA / MOD / "recipe/crafting" / f"{name}.json", {
        "type": "minecraft:crafting_shapeless",
        "category": "misc",
        "ingredients": ingredients,
        "result": res,
    })

def energizing(name, energy, ingredients, result, count=1, modid=None):
    res = {"id": result}
    if count > 1:
        res = {"count": count, "id": result}
    conditions = {}
    if modid:
        conditions = {"neoforge:conditions": [{"type": "neoforge:mod_loaded", "modid": modid}]}
    write_json(DATA / MOD / "recipe/energizing" / f"{name}.json", {
        **conditions,
        "type": "powah:energizing",
        "energy": energy,
        "ingredients": ingredients,
        "result": res,
    })

def recipes():

    energizing("voltaic_crystal", 120_000_000,
               ["powah:nitro_crystal_block", "#c:storage_blocks/diamond", "#c:storage_blocks/lapis", "#c:nether_stars"],
               f"{MOD}:crystal_voltaic", 8)
    energizing("astral_crystal", 600_000_000,
               [f"{MOD}:voltaic_crystal_block", "#c:storage_blocks/netherite", "minecraft:dragon_breath", "minecraft:end_crystal"],
               f"{MOD}:crystal_astral", 4)
    energizing("quantum_crystal", 3_000_000_000,
               [f"{MOD}:astral_crystal_block", "#c:storage_blocks/netherite", "minecraft:echo_shard", "#c:nether_stars"],
               f"{MOD}:crystal_quantum", 4)

    for tier in TIERS:
        ns, prev = PREVIOUS[tier]
        crystal = f"{MOD}:crystal_{tier}"
        capacitor = f"{MOD}:capacitor_{tier}"
        casing = "powah:dielectric_casing"

        shapeless(f"{tier}_crystal_block", [crystal] * 9, f"{MOD}:{tier}_crystal_block")
        shapeless(f"{tier}_crystal", [f"{MOD}:{tier}_crystal_block"], crystal, 9)

        shaped(f"capacitor_{tier}", ["pbp", "bcb", "pbp"],
               {"b": crystal, "c": f"{ns}:capacitor_{prev}", "p": "powah:dielectric_paste"}, capacitor)
        shaped(f"battery_{tier}", ["oko", "iri", "oco"],
               {"c": f"{ns}:battery_{prev}", "i": capacitor, "k": crystal, "o": "powah:dielectric_paste",
                "r": "#c:storage_blocks/redstone"}, f"{MOD}:battery_{tier}")
        shaped(f"energy_cell_{tier}", ["bnb", "cdc", "bnb"],
               {"b": crystal, "c": f"{ns}:energy_cell_{prev}", "d": casing, "n": capacitor}, f"{MOD}:energy_cell_{tier}")
        shaped(f"energy_cable_{tier}", ["ddd", "tct", "ddd"],
               {"c": capacitor, "d": "powah:dielectric_rod_horizontal", "t": f"{ns}:energy_cable_{prev}"},
               f"{MOD}:energy_cable_{tier}", 6)
        shaped(f"energy_cable_{tier}_2", ["ddd", "kck", "ddd"],
               {"c": capacitor, "d": "powah:dielectric_rod_horizontal", "k": crystal}, f"{MOD}:energy_cable_{tier}", 12)
        shaped(f"solar_panel_{tier}", ["ppp", "cdc", "eee"],
               {"c": capacitor, "d": casing, "e": crystal, "p": f"{ns}:solar_panel_{prev}"}, f"{MOD}:solar_panel_{tier}")
        shaped(f"thermo_generator_{tier}", ["igi", "cdc", "ptp"],
               {"c": capacitor, "d": casing, "g": crystal, "i": "powah:dielectric_paste",
                "p": "powah:thermoelectric_plate", "t": f"{ns}:thermo_generator_{prev}"}, f"{MOD}:thermo_generator_{tier}")
        shaped(f"furnator_{tier}", ["ggg", "cdc", "gfg"],
               {"c": capacitor, "d": casing, "f": f"{ns}:furnator_{prev}", "g": crystal}, f"{MOD}:furnator_{tier}")
        shaped(f"magmator_{tier}", ["ggg", "cdc", "gfg"],
               {"c": capacitor, "d": casing, "f": f"{ns}:magmator_{prev}", "g": crystal}, f"{MOD}:magmator_{tier}")

        shaped(f"energizing_rod_{tier}", [" q ", "bcb", " h "],
               {"b": capacitor, "c": casing, "h": f"{ns}:energizing_rod_{prev}", "q": "#c:storage_blocks/quartz"},
               f"{MOD}:energizing_rod_{tier}")
        shaped(f"ender_cell_{tier}", ["oio", "ici", "oio"],
               {"c": "powah:ender_core", "i": crystal, "o": "#c:obsidians/normal"}, f"{MOD}:ender_cell_{tier}")

        shapeless(f"ender_cell_{tier}_2", [f"{MOD}:ender_cell_{tier}"], f"{MOD}:ender_cell_{tier}")
        shaped(f"ender_gate_{tier}", ["oio", "ici", "oio"],
               {"c": "powah:ender_core", "i": f"{MOD}:energy_cable_{tier}", "o": "#c:obsidians/normal"}, f"{MOD}:ender_gate_{tier}", 4)
        shapeless(f"ender_gate_{tier}_2", [f"{MOD}:ender_gate_{tier}"], f"{MOD}:ender_gate_{tier}")
        shaped(f"player_transmitter_{tier}", [" p ", "ici", " r "],
               {"c": casing, "i": capacitor, "p": f"{ns}:player_transmitter_{prev}", "r": "powah:dielectric_rod"},
               f"{MOD}:player_transmitter_{tier}")
        shaped(f"reactor_{tier}", ["rlr", "lul", "rlr"],
               {"l": capacitor, "r": f"{ns}:reactor_{prev}", "u": "powah:uraninite"}, f"{MOD}:reactor_{tier}", 4)
        shaped(f"energy_hopper_{tier}", ["ppp", "cdc", "php"],
               {"c": capacitor, "d": casing, "h": f"{ns}:energy_hopper_{prev}", "p": "powah:dielectric_paste"},
               f"{MOD}:energy_hopper_{tier}")
        shaped(f"energy_discharger_{tier}", ["pcp", "pdp", "pcp"],
               {"c": capacitor, "d": casing, "p": "powah:dielectric_paste"}, f"{MOD}:energy_discharger_{tier}")

    shaped("lunar_panel", ["ppp", "ldl", "ece"],
           {"p": "powah:photoelectric_pane", "l": "#c:storage_blocks/lapis", "d": "powah:dielectric_casing",
            "e": "powah:crystal_niotic", "c": "powah:capacitor_niotic"}, f"{MOD}:lunar_panel")
    shaped("hybrid_panel", ["sgl", "cdc", "eee"],
           {"s": "powah:solar_panel_nitro", "g": "#c:storage_blocks/gold", "l": f"{MOD}:lunar_panel",
            "d": "powah:dielectric_casing", "c": "powah:capacitor_nitro", "e": "powah:crystal_nitro"}, f"{MOD}:hybrid_panel")

    energizing("compat/ae2_charged_certus_quartz_crystal", 10_000,
               ["ae2:certus_quartz_crystal"], "ae2:charged_certus_quartz_crystal", modid="ae2")
    energizing("compat/ae2_fluix_crystal", 30_000,
               ["ae2:charged_certus_quartz_crystal", "#c:dusts/redstone", "#c:gems/quartz"], "ae2:fluix_crystal", 2, modid="ae2")

def tags():
    def tag(ns, kind, name, values):
        write_json(DATA / ns / "tags" / kind / f"{name}.json", {"values": values})

    tiered_blocks = ["energy_cell", "ender_cell", "ender_gate", "energy_cable", "energizing_rod", "furnator", "magmator", "thermo_generator",
                     "solar_panel", "reactor", "player_transmitter", "energy_hopper", "energy_discharger"]
    all_blocks = [f"{MOD}:{b}_{t}" for b in tiered_blocks for t in TIERS]
    all_blocks += [f"{MOD}:{t}_crystal_block" for t in TIERS]
    all_blocks += [f"{MOD}:lunar_panel", f"{MOD}:hybrid_panel"]
    tag("minecraft", "block", "mineable/pickaxe", all_blocks)

    for powah_tag, block in (("energy_cells", "energy_cell"), ("ender_cells", "ender_cell"), ("ender_gates", "ender_gate"), ("energy_cables", "energy_cable"),
                             ("energizing_rods", "energizing_rod"), ("furnators", "furnator"), ("magmators", "magmator"),
                             ("thermo_generators", "thermo_generator"), ("solar_panels", "solar_panel"), ("reactors", "reactor"),
                             ("player_transmitters", "player_transmitter"), ("energy_hoppers", "energy_hopper"),
                             ("energy_dischargers", "energy_discharger")):
        tag("powah", "block", powah_tag, [f"{MOD}:{block}_{t}" for t in TIERS])
        tag("powah", "item", powah_tag, [f"{MOD}:{block}_{t}" for t in TIERS])

    crystals = [f"{MOD}:crystal_{t}" for t in TIERS]
    crystal_blocks = [f"{MOD}:{t}_crystal_block" for t in TIERS]
    tag("c", "item", "gems", crystals)
    for kind in ("block", "item"):
        tag("c", kind, "storage_blocks", crystal_blocks)

    tag("curios", "item", "curio", [f"{MOD}:battery_{t}" for t in TIERS])

def loot_tables():
    for tier in TIERS:
        name = f"{tier}_crystal_block"
        write_json(DATA / MOD / "loot_table/blocks" / f"{name}.json", {
            "type": "minecraft:block",
            "pools": [{
                "rolls": 1,
                "entries": [{"type": "minecraft:item", "name": f"{MOD}:{name}"}],
                "conditions": [{"condition": "minecraft:survives_explosion"}],
            }],
        })

def data_maps():

    maps = DATA / "powah/data_maps"

    heat = {f"{MOD}:{tier}_crystal_block": {"temperature": temp} for tier, temp in CRYSTAL_BLOCK_HEAT.items()}
    heat["minecraft:fire"] = {"temperature": 700}
    heat["minecraft:soul_fire"] = {"temperature": 900}
    heat["mekanism:superheating_element"] = {
        "neoforge:conditions": [{"type": "neoforge:mod_loaded", "modid": "mekanism"}],
        "temperature": 3000,
    }
    write_json(maps / "block/heat_source.json", {"values": heat})
    write_json(maps / "fluid/heat_source.json", {"values": {
        "#c:superheated_sodium": {"temperature": 2000},
    }})

    write_json(maps / "fluid/magmator_fuel.json", {"values": {
        "#c:superheated_sodium": {"energy_produced": 25000},
        "#c:bioethanol": {"energy_produced": 6000},
    }})

    write_json(maps / "item/reactor_fuel.json", {"values": {
        "#c:ingots/uranium": {"fuelAmount": 100, "temperature": 700},
        "#c:pellets/plutonium": {"fuelAmount": 300, "temperature": 1000},
        "#c:pellets/polonium": {"fuelAmount": 500, "temperature": 1400},
        "#c:pellets/antimatter": {"fuelAmount": 2000, "temperature": 2500},
    }})

def main():
    for sub in ("textures", "models", "blockstates", "items", "lang"):
        shutil.rmtree(ASSETS / sub, ignore_errors=True)
    shutil.rmtree(DATA, ignore_errors=True)
    tier_textures()
    machine_textures()
    models()
    lang()
    recipes()
    tags()
    loot_tables()
    data_maps()
    print("fertig:", sum(1 for p in RES.rglob("*") if p.is_file()), "Dateien in", RES)

if __name__ == "__main__":
    main()
