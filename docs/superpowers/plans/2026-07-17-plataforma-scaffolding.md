# Plataforma del Curso — Scaffolding Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Scaffold the Astro-based course platform (`site/`) — Tailwind, content collections, the pedagogical interactive components, progress tracking — and prove the whole pipeline end-to-end by publishing one real lesson (Etapa 0 / Lección 1) backed by real, compiled Java code in `proyecto/`.

**Architecture:** Astro static site (`site/`) using content collections (`glob()` loader + Zod schema) for lessons under `src/content/lecciones/`. Interactive mechanics are native HTML (`<details>`) plus small vanilla-TS modules — no UI framework. `site/progress.json` is the build-time catalog of published lessons; `localStorage` holds the user's own completion state. `proyecto/` is a separate, plain Java source tree (no build tool yet — Maven is introduced later, at the etapa the course itself teaches Maven) holding the real code shown in lessons.

**Tech Stack:** Astro 5.x, Tailwind CSS v4 (`@tailwindcss/vite`), TypeScript, Vitest + Astro's experimental Container API for component/logic tests, Node v24.18.0 / npm 11.16.0 (already installed on this machine), Java 21 (`javac`/`java` directly, no build tool yet).

## Global Constraints

- No UI framework (React/Vue/Svelte) — native HTML + vanilla TS only. (spec §4.1)
- No AI backend, no code-execution sandbox, no user accounts. (spec §2)
- All site copy and lesson content: español técnico neutro, sin voseo. (spec §4.2, confirmed by user)
- `progress.json` = catalog of published lessons, maintained by the builder (me); `localStorage` = user's own completion state. Never conflate the two. (spec §6)
- Every Java code example shown in a lesson must come from `proyecto/`, compiled and run before being pasted in. (spec §5.3)
- Maven is **not** introduced in `proyecto/` until the course itself reaches the Maven etapa — Etapa 0/1 content teaches manual `javac`/`java` compilation, so the reference code must match that (no build tool yet).
- Repo root: `/home/senpai/cursodejava`. Platform: `site/`. Java reference project: `proyecto/`.
- Every non-trivial piece of logic (schema validation, pure functions, component rendering) gets a failing test before its implementation (strict TDD).

---

### Task 1: Scaffold the Astro project

**Files:**
- Create: `site/` (via `create-astro` CLI, minimal template)

**Interfaces:**
- Produces: a working Astro project at `site/` with `package.json`, `astro.config.mjs`, `src/pages/index.astro`.

- [ ] **Step 1: Scaffold**

Run from repo root (`/home/senpai/cursodejava`):

```bash
npm create astro@latest site -- --template minimal --install --no-git --yes --skip-houston
```

`--no-git` because the outer repo already has git initialized; we don't want a nested repo.

- [ ] **Step 2: Verify the baseline build**

```bash
cd site && npm run build
```

Expected: `astro build` completes successfully, `site/dist/index.html` exists.

- [ ] **Step 3: Commit**

```bash
cd /home/senpai/cursodejava
git add site/
git commit -m "chore(site): scaffold Astro project"
```

---

### Task 2: Add Tailwind CSS v4

**Files:**
- Modify: `site/astro.config.mjs`
- Create: `site/src/styles/global.css`
- Modify: `site/src/pages/index.astro`

**Interfaces:**
- Produces: `site/src/styles/global.css` importable by any layout/page going forward.

- [ ] **Step 1: Install packages**

```bash
cd site
npm install tailwindcss @tailwindcss/vite
```

- [ ] **Step 2: Wire the Vite plugin**

Edit `site/astro.config.mjs`:

```javascript
import { defineConfig } from 'astro/config';
import tailwindcss from '@tailwindcss/vite';

export default defineConfig({
  vite: {
    plugins: [tailwindcss()],
  },
});
```

- [ ] **Step 3: Create the global stylesheet**

Create `site/src/styles/global.css`:

```css
@import "tailwindcss";
```

- [ ] **Step 4: Prove it compiles — import in the stub page**

Edit `site/src/pages/index.astro` to import the stylesheet and use a Tailwind utility class:

```astro
---
import '../styles/global.css';
---
<h1 class="text-3xl font-bold underline">cursodejava</h1>
```

- [ ] **Step 5: Verify**

```bash
npm run build
grep -q "font-weight" dist/_astro/*.css && echo "TAILWIND OK"
```

Expected output: `TAILWIND OK` (confirms Tailwind's compiled CSS landed in the build output).

- [ ] **Step 6: Commit**

```bash
cd /home/senpai/cursodejava
git add site/
git commit -m "feat(site): add Tailwind CSS v4"
```

---

### Task 3: Vitest setup + lesson frontmatter schema

**Files:**
- Create: `site/vitest.config.ts`
- Create: `site/src/content/schema.ts`
- Create: `site/src/content/schema.test.ts`
- Create: `site/src/content.config.ts`

**Interfaces:**
- Produces: `leccionSchema` (exported from `site/src/content/schema.ts`) — a Zod object schema with fields `{ etapa: number, etapaTitulo: string, leccion: number, titulo: string, objetivo: string, prerrequisitos: string[], version_proyecto: string }`. Later tasks (layout, pages) rely on entries typed by this schema via the `lecciones` collection.

- [ ] **Step 1: Install Vitest**

```bash
cd site
npm install -D vitest
```

- [ ] **Step 2: Vitest config**

Create `site/vitest.config.ts`:

```typescript
/// <reference types="vitest/config" />
import { getViteConfig } from 'astro/config';

export default getViteConfig({
  test: {},
});
```

- [ ] **Step 3: Write the failing test**

Create `site/src/content/schema.test.ts`:

```typescript
import { describe, test, expect } from 'vitest';
import { leccionSchema } from './schema';

describe('leccionSchema', () => {
  test('accepts valid lesson frontmatter', () => {
    const valid = {
      etapa: 0,
      etapaTitulo: 'Cómo funciona Java',
      leccion: 1,
      titulo: 'Qué ocurre cuando ejecutas un programa Java',
      objetivo: 'Explicar el camino desde el código fuente hasta la ejecución en la JVM',
      prerrequisitos: [],
      version_proyecto: 'v0 — Hola Mundo (javac/java manual)',
    };
    expect(leccionSchema.safeParse(valid).success).toBe(true);
  });

  test('rejects frontmatter missing objetivo', () => {
    const invalid = {
      etapa: 0,
      etapaTitulo: 'Cómo funciona Java',
      leccion: 1,
      titulo: 'Qué ocurre cuando ejecutas un programa Java',
      prerrequisitos: [],
      version_proyecto: 'v0 — Hola Mundo (javac/java manual)',
    };
    expect(leccionSchema.safeParse(invalid).success).toBe(false);
  });

  test('rejects negative etapa', () => {
    const invalid = {
      etapa: -1,
      etapaTitulo: 'Cómo funciona Java',
      leccion: 1,
      titulo: 'X',
      objetivo: 'X',
      prerrequisitos: [],
      version_proyecto: 'v0',
    };
    expect(leccionSchema.safeParse(invalid).success).toBe(false);
  });
});
```

- [ ] **Step 4: Run test to verify it fails**

```bash
npx vitest run src/content/schema.test.ts
```

Expected: FAIL — `Cannot find module './schema'`.

- [ ] **Step 5: Implement the schema**

Create `site/src/content/schema.ts`:

```typescript
import { z } from 'astro/zod';

export const leccionSchema = z.object({
  etapa: z.number().int().min(0),
  etapaTitulo: z.string().min(1),
  leccion: z.number().int().min(1),
  titulo: z.string().min(1),
  objetivo: z.string().min(1),
  prerrequisitos: z.array(z.string()),
  version_proyecto: z.string().min(1),
});

export type Leccion = z.infer<typeof leccionSchema>;
```

- [ ] **Step 6: Run test to verify it passes**

```bash
npx vitest run src/content/schema.test.ts
```

Expected: 3 passed.

- [ ] **Step 7: Register the collection**

Create `site/src/content.config.ts`:

```typescript
import { defineCollection } from 'astro:content';
import { glob } from 'astro/loaders';
import { leccionSchema } from './content/schema';

const lecciones = defineCollection({
  loader: glob({ pattern: '**/*.mdx', base: './src/content/lecciones' }),
  schema: leccionSchema,
});

export const collections = { lecciones };
```

- [ ] **Step 8: Commit**

```bash
cd /home/senpai/cursodejava
git add site/
git commit -m "feat(site): add Vitest and lesson content-collection schema"
```

---

### Task 4: Base layout + routing for lessons

**Files:**
- Modify: `site/astro.config.mjs` (register the MDX integration)
- Create: `site/src/layouts/LeccionLayout.astro`
- Create: `site/src/layouts/LeccionLayout.test.ts`
- Create: `site/src/pages/lecciones/[...slug].astro`
- Create: `site/src/content/lecciones/etapa-00-como-funciona-java/leccion-01-que-ocurre-cuando-ejecutas-java.mdx` (stub — real content lands in Task 10)

**Interfaces:**
- Consumes: `leccionSchema` / `Leccion` type from Task 3.
- Produces: `LeccionLayout` component with props `{ titulo: string, etapaTitulo: string }` and a default slot for the MDX body — later tasks (and every future lesson) render through it.

**Correction (found during implementation, 2026-07-17):** lesson content uses `.mdx`, and Astro only parses `.md` out of the box — `.mdx` requires the official `@astrojs/mdx` integration, or the glob loader reports "No entry type found" and the collection resolves empty. This step was missing from the original plan; added as Step 0 below.

- [ ] **Step 0: Install and register the MDX integration**

```bash
cd site
npm install @astrojs/mdx
```

Edit `site/astro.config.mjs` to add it to `integrations`:

```javascript
import { defineConfig } from 'astro/config';
import tailwindcss from '@tailwindcss/vite';
import mdx from '@astrojs/mdx';

export default defineConfig({
  integrations: [mdx()],
  vite: {
    plugins: [tailwindcss()],
  },
});
```

- [ ] **Step 1: Write the failing container test**

Create `site/src/layouts/LeccionLayout.test.ts`:

```typescript
import { experimental_AstroContainer as AstroContainer } from 'astro/container';
import { expect, test } from 'vitest';
import LeccionLayout from './LeccionLayout.astro';

test('renders titulo, etapaTitulo and slot content', async () => {
  const container = await AstroContainer.create();
  const result = await container.renderToString(LeccionLayout, {
    props: { titulo: 'Qué ocurre cuando ejecutas un programa Java', etapaTitulo: 'Cómo funciona Java' },
    slots: { default: '<p>cuerpo de la lección</p>' },
  });

  expect(result).toContain('Qué ocurre cuando ejecutas un programa Java');
  expect(result).toContain('Cómo funciona Java');
  expect(result).toContain('cuerpo de la lección');
});
```

- [ ] **Step 2: Run test to verify it fails**

```bash
npx vitest run src/layouts/LeccionLayout.test.ts
```

Expected: FAIL — module not found.

- [ ] **Step 3: Implement the layout**

Create `site/src/layouts/LeccionLayout.astro`:

```astro
---
import '../styles/global.css';

interface Props {
  titulo: string;
  etapaTitulo: string;
}
const { titulo, etapaTitulo } = Astro.props;
---
<html lang="es">
  <head>
    <meta charset="utf-8" />
    <title>{titulo} — cursodejava</title>
  </head>
  <body class="mx-auto max-w-3xl px-4 py-8">
    <p class="text-sm uppercase tracking-wide text-gray-500">{etapaTitulo}</p>
    <h1 class="text-3xl font-bold mb-6">{titulo}</h1>
    <article class="prose max-w-none">
      <slot />
    </article>
  </body>
</html>
```

- [ ] **Step 4: Run test to verify it passes**

```bash
npx vitest run src/layouts/LeccionLayout.test.ts
```

Expected: 1 passed.

- [ ] **Step 5: Dynamic route for lessons**

Create `site/src/pages/lecciones/[...slug].astro`:

```astro
---
import { getCollection, render } from 'astro:content';
import LeccionLayout from '../../layouts/LeccionLayout.astro';

export async function getStaticPaths() {
  const entries = await getCollection('lecciones');
  return entries.map((entry) => ({
    params: { slug: entry.id },
    props: { entry },
  }));
}

const { entry } = Astro.props;
const { Content } = await render(entry);
---
<LeccionLayout titulo={entry.data.titulo} etapaTitulo={entry.data.etapaTitulo}>
  <Content />
</LeccionLayout>
```

- [ ] **Step 6: Stub lesson to prove the route resolves**

Create `site/src/content/lecciones/etapa-00-como-funciona-java/leccion-01-que-ocurre-cuando-ejecutas-java.mdx`:

```mdx
---
etapa: 0
etapaTitulo: "Cómo funciona Java"
leccion: 1
titulo: "Qué ocurre cuando ejecutas un programa Java"
objetivo: "Explicar el camino desde el código fuente hasta la ejecución en la JVM."
prerrequisitos: []
version_proyecto: "v0 — Hola Mundo (javac/java manual)"
---

Contenido pendiente (Task 10).
```

- [ ] **Step 7: Verify the route builds**

```bash
npm run build
test -f dist/lecciones/etapa-00-como-funciona-java/leccion-01-que-ocurre-cuando-ejecutas-java/index.html && echo "ROUTE OK"
```

Expected: `ROUTE OK`.

- [ ] **Step 8: Commit**

```bash
cd /home/senpai/cursodejava
git add site/
git commit -m "feat(site): add lesson layout and dynamic routing"
```

---

### Task 5: `<Reveal>` component

**Files:**
- Create: `site/src/components/Reveal.astro`
- Create: `site/src/components/Reveal.test.ts`

**Interfaces:**
- Produces: `Reveal` component, props `{ titulo: string }`, default slot = hidden content. Used by every future lesson for "predice → resultado", hints, and solutions.

- [ ] **Step 1: Write the failing test**

Create `site/src/components/Reveal.test.ts`:

```typescript
import { experimental_AstroContainer as AstroContainer } from 'astro/container';
import { expect, test } from 'vitest';
import Reveal from './Reveal.astro';

test('renders a native details/summary with the given title and hidden content', async () => {
  const container = await AstroContainer.create();
  const result = await container.renderToString(Reveal, {
    props: { titulo: 'Ver resultado' },
    slots: { default: '<p>42</p>' },
  });

  expect(result).toContain('<details');
  expect(result).toContain('<summary');
  expect(result).toContain('Ver resultado');
  expect(result).toContain('<p>42</p>');
});
```

- [ ] **Step 2: Run test to verify it fails**

```bash
npx vitest run src/components/Reveal.test.ts
```

Expected: FAIL — module not found.

- [ ] **Step 3: Implement**

Create `site/src/components/Reveal.astro`:

```astro
---
interface Props {
  titulo: string;
}
const { titulo } = Astro.props;
---
<details class="my-4 rounded border border-gray-300 p-3">
  <summary class="cursor-pointer font-semibold">{titulo}</summary>
  <div class="mt-2">
    <slot />
  </div>
</details>
```

- [ ] **Step 4: Run test to verify it passes**

```bash
npx vitest run src/components/Reveal.test.ts
```

Expected: 1 passed.

- [ ] **Step 5: Commit**

```bash
cd /home/senpai/cursodejava
git add site/
git commit -m "feat(site): add Reveal component"
```

---

### Task 6: `<Quiz>` component

**Files:**
- Create: `site/src/lib/quiz.ts`
- Create: `site/src/lib/quiz.test.ts`
- Create: `site/src/components/Quiz.astro`
- Create: `site/src/components/Quiz.test.ts`

**Interfaces:**
- Produces: `evaluarRespuesta(opciones: QuizOpcion[], indiceSeleccionado: number): { correcta: boolean; feedback: string }` (pure, exported from `site/src/lib/quiz.ts`) and `QuizOpcion = { texto: string; correcta: boolean; feedback: string }`. The `Quiz.astro` component wires this to a click handler.

- [ ] **Step 1: Write the failing test for the pure logic**

Create `site/src/lib/quiz.test.ts`:

```typescript
import { describe, test, expect } from 'vitest';
import { evaluarRespuesta, type QuizOpcion } from './quiz';

const opciones: QuizOpcion[] = [
  { texto: 'ArrayList', correcta: false, feedback: 'ArrayList no garantiza orden por clave.' },
  { texto: 'HashMap', correcta: true, feedback: 'Correcto: HashMap asocia clave-valor sin orden garantizado.' },
  { texto: 'HashSet', correcta: false, feedback: 'HashSet no almacena pares clave-valor.' },
];

describe('evaluarRespuesta', () => {
  test('returns correcta=true and the feedback of the correct option', () => {
    const resultado = evaluarRespuesta(opciones, 1);
    expect(resultado.correcta).toBe(true);
    expect(resultado.feedback).toBe('Correcto: HashMap asocia clave-valor sin orden garantizado.');
  });

  test('returns correcta=false and the feedback of the wrong option chosen', () => {
    const resultado = evaluarRespuesta(opciones, 0);
    expect(resultado.correcta).toBe(false);
    expect(resultado.feedback).toBe('ArrayList no garantiza orden por clave.');
  });

  test('throws on an out-of-range index', () => {
    expect(() => evaluarRespuesta(opciones, 99)).toThrow();
  });
});
```

- [ ] **Step 2: Run test to verify it fails**

```bash
npx vitest run src/lib/quiz.test.ts
```

Expected: FAIL — module not found.

- [ ] **Step 3: Implement the pure function**

Create `site/src/lib/quiz.ts`:

```typescript
export interface QuizOpcion {
  texto: string;
  correcta: boolean;
  feedback: string;
}

export function evaluarRespuesta(opciones: QuizOpcion[], indiceSeleccionado: number) {
  const opcion = opciones[indiceSeleccionado];
  if (!opcion) {
    throw new Error(`Índice de opción fuera de rango: ${indiceSeleccionado}`);
  }
  return { correcta: opcion.correcta, feedback: opcion.feedback };
}
```

- [ ] **Step 4: Run test to verify it passes**

```bash
npx vitest run src/lib/quiz.test.ts
```

Expected: 3 passed.

- [ ] **Step 5: Write the failing container test for the component's initial render**

Create `site/src/components/Quiz.test.ts`:

```typescript
import { experimental_AstroContainer as AstroContainer } from 'astro/container';
import { expect, test } from 'vitest';
import Quiz from './Quiz.astro';

test('renders the question and one button per option', async () => {
  const container = await AstroContainer.create();
  const result = await container.renderToString(Quiz, {
    props: {
      pregunta: '¿Qué colección asocia clave-valor sin orden garantizado?',
      opciones: [
        { texto: 'ArrayList', correcta: false, feedback: 'No es clave-valor.' },
        { texto: 'HashMap', correcta: true, feedback: 'Correcto.' },
      ],
    },
  });

  expect(result).toContain('¿Qué colección asocia clave-valor sin orden garantizado?');
  expect(result).toContain('ArrayList');
  expect(result).toContain('HashMap');
});
```

- [ ] **Step 6: Run test to verify it fails**

```bash
npx vitest run src/components/Quiz.test.ts
```

Expected: FAIL — module not found.

- [ ] **Step 7: Implement the component**

Create `site/src/components/Quiz.astro`:

```astro
---
import type { QuizOpcion } from '../lib/quiz';

interface Props {
  pregunta: string;
  opciones: QuizOpcion[];
}
const { pregunta, opciones } = Astro.props;
const quizId = `quiz-${Math.random().toString(36).slice(2, 9)}`;
---
<div class="my-4 rounded border border-gray-300 p-4" data-quiz={quizId} data-opciones={JSON.stringify(opciones)}>
  <p class="font-semibold mb-2">{pregunta}</p>
  <div class="flex flex-col gap-2">
    {opciones.map((opcion, i) => (
      <button type="button" class="text-left rounded border px-3 py-2 hover:bg-gray-50" data-indice={i}>
        {opcion.texto}
      </button>
    ))}
  </div>
  <p class="mt-3 font-medium" data-feedback></p>
</div>

<script>
  import { evaluarRespuesta, type QuizOpcion } from '../lib/quiz';

  document.querySelectorAll<HTMLElement>('[data-quiz]').forEach((quizEl) => {
    const opciones: QuizOpcion[] = JSON.parse(quizEl.dataset.opciones ?? '[]');
    const feedbackEl = quizEl.querySelector<HTMLElement>('[data-feedback]');

    quizEl.querySelectorAll<HTMLButtonElement>('button[data-indice]').forEach((boton) => {
      boton.addEventListener('click', () => {
        const indice = Number(boton.dataset.indice);
        const resultado = evaluarRespuesta(opciones, indice);
        if (feedbackEl) {
          feedbackEl.textContent = resultado.feedback;
          feedbackEl.className = resultado.correcta
            ? 'mt-3 font-medium text-green-700'
            : 'mt-3 font-medium text-red-700';
        }
      });
    });
  });
</script>
```

- [ ] **Step 8: Run test to verify it passes**

```bash
npx vitest run src/components/Quiz.test.ts
```

Expected: 1 passed.

- [ ] **Step 9: Commit**

```bash
cd /home/senpai/cursodejava
git add site/
git commit -m "feat(site): add Quiz component with per-option feedback"
```

---

### Task 7: `<SelfCheck>` component and localStorage progress helpers

**Files:**
- Create: `site/src/lib/progresoStorage.ts`
- Create: `site/src/lib/progresoStorage.test.ts`
- Create: `site/src/components/SelfCheck.astro`

**Interfaces:**
- Produces: `guardarSelfCheck(leccionId: string, nivel: NivelDominio): void`, `leerSelfCheck(leccionId: string): NivelDominio | null`, `marcarCompletada(leccionId: string): void`, `leerCompletadas(): string[]` (all in `site/src/lib/progresoStorage.ts`). Task 9's progress dashboard consumes `leerCompletadas`.

- [ ] **Step 1: Install jsdom for DOM-backed tests**

```bash
cd site
npm install -D jsdom
```

- [ ] **Step 2: Write the failing test**

Create `site/src/lib/progresoStorage.test.ts`:

```typescript
// @vitest-environment jsdom
import { describe, test, expect, beforeEach } from 'vitest';
import { guardarSelfCheck, leerSelfCheck, marcarCompletada, leerCompletadas } from './progresoStorage';

beforeEach(() => {
  localStorage.clear();
});

describe('progresoStorage', () => {
  test('guardarSelfCheck then leerSelfCheck round-trips the level', () => {
    guardarSelfCheck('etapa-00/leccion-01', 'DOMINADO');
    expect(leerSelfCheck('etapa-00/leccion-01')).toBe('DOMINADO');
  });

  test('leerSelfCheck returns null when nothing was saved', () => {
    expect(leerSelfCheck('etapa-00/leccion-02')).toBeNull();
  });

  test('marcarCompletada adds the lesson id without duplicating it', () => {
    marcarCompletada('etapa-00/leccion-01');
    marcarCompletada('etapa-00/leccion-01');
    expect(leerCompletadas()).toEqual(['etapa-00/leccion-01']);
  });
});
```

- [ ] **Step 3: Run test to verify it fails**

```bash
npx vitest run src/lib/progresoStorage.test.ts
```

Expected: FAIL — module not found.

- [ ] **Step 4: Implement**

Create `site/src/lib/progresoStorage.ts`:

```typescript
export type NivelDominio = 'DOMINADO' | 'PARCIAL' | 'INSUFICIENTE';

const SELFCHECK_PREFIX = 'cursodejava:selfcheck:';
const COMPLETADAS_KEY = 'cursodejava:completadas';

export function guardarSelfCheck(leccionId: string, nivel: NivelDominio): void {
  localStorage.setItem(SELFCHECK_PREFIX + leccionId, nivel);
}

export function leerSelfCheck(leccionId: string): NivelDominio | null {
  return localStorage.getItem(SELFCHECK_PREFIX + leccionId) as NivelDominio | null;
}

export function marcarCompletada(leccionId: string): void {
  const actuales = leerCompletadas();
  if (!actuales.includes(leccionId)) {
    localStorage.setItem(COMPLETADAS_KEY, JSON.stringify([...actuales, leccionId]));
  }
}

export function leerCompletadas(): string[] {
  const raw = localStorage.getItem(COMPLETADAS_KEY);
  return raw ? JSON.parse(raw) : [];
}
```

- [ ] **Step 5: Run test to verify it passes**

```bash
npx vitest run src/lib/progresoStorage.test.ts
```

Expected: 3 passed.

- [ ] **Step 6: Implement the `<SelfCheck>` component** (presentational, wires the tested helpers — no new logic to unit test)

Create `site/src/components/SelfCheck.astro`:

```astro
---
interface Props {
  leccionId: string;
  criterios: string[];
}
const { leccionId, criterios } = Astro.props;
---
<div class="my-4 rounded border border-gray-300 p-4" data-selfcheck data-leccion-id={leccionId}>
  <p class="font-semibold mb-2">¿Cómo quedó tu dominio de esta lección?</p>
  <ul class="list-disc pl-5 mb-3 text-sm text-gray-700">
    {criterios.map((c) => <li>{c}</li>)}
  </ul>
  <div class="flex gap-2">
    <button type="button" class="rounded border px-3 py-1 hover:bg-green-50" data-nivel="DOMINADO">Dominado</button>
    <button type="button" class="rounded border px-3 py-1 hover:bg-yellow-50" data-nivel="PARCIAL">Parcial</button>
    <button type="button" class="rounded border px-3 py-1 hover:bg-red-50" data-nivel="INSUFICIENTE">Insuficiente</button>
  </div>
  <p class="mt-2 text-sm text-gray-600" data-selfcheck-status></p>
</div>

<script>
  import { guardarSelfCheck, marcarCompletada, type NivelDominio } from '../lib/progresoStorage';

  document.querySelectorAll<HTMLElement>('[data-selfcheck]').forEach((el) => {
    const leccionId = el.dataset.leccionId!;
    const statusEl = el.querySelector<HTMLElement>('[data-selfcheck-status]');

    el.querySelectorAll<HTMLButtonElement>('button[data-nivel]').forEach((boton) => {
      boton.addEventListener('click', () => {
        const nivel = boton.dataset.nivel as NivelDominio;
        guardarSelfCheck(leccionId, nivel);
        marcarCompletada(leccionId);
        if (statusEl) statusEl.textContent = `Guardado: ${nivel}`;
      });
    });
  });
</script>
```

- [ ] **Step 7: Commit**

```bash
cd /home/senpai/cursodejava
git add site/
git commit -m "feat(site): add SelfCheck component and localStorage progress helpers"
```

---

### Task 8: `<ErrorChallenge>` and `<Comparativa>` components

**Files:**
- Create: `site/src/components/ErrorChallenge.astro`
- Create: `site/src/components/ErrorChallenge.test.ts`
- Create: `site/src/components/Comparativa.astro`
- Create: `site/src/components/Comparativa.test.ts`

**Interfaces:**
- Produces: `ErrorChallenge` (props `{ sintoma: string, diagnostico: string, causa: string, solucion: string, prevencion: string }`, default slot = buggy code). `Comparativa` (props `{ columnas: string[], filas: string[][] }`).

- [ ] **Step 1: Write the failing test for ErrorChallenge**

Create `site/src/components/ErrorChallenge.test.ts`:

```typescript
import { experimental_AstroContainer as AstroContainer } from 'astro/container';
import { expect, test } from 'vitest';
import ErrorChallenge from './ErrorChallenge.astro';

test('renders symptom directly and diagnosis/cause/fix behind a reveal', async () => {
  const container = await AstroContainer.create();
  const result = await container.renderToString(ErrorChallenge, {
    props: {
      sintoma: 'NullPointerException al llamar getNombre()',
      diagnostico: 'El objeto Cliente nunca se inicializó.',
      causa: 'El constructor no asignó el campo.',
      solucion: 'Inicializar el campo en el constructor.',
      prevencion: 'Validar campos obligatorios en el constructor.',
    },
    slots: { default: '<pre>Cliente c;\nc.getNombre();</pre>' },
  });

  expect(result).toContain('NullPointerException al llamar getNombre()');
  expect(result).toContain('El objeto Cliente nunca se inicializó.');
  expect(result).toContain('<details');
});
```

- [ ] **Step 2: Run to verify it fails**

```bash
npx vitest run src/components/ErrorChallenge.test.ts
```

Expected: FAIL — module not found.

- [ ] **Step 3: Implement ErrorChallenge**

Create `site/src/components/ErrorChallenge.astro`:

```astro
---
interface Props {
  sintoma: string;
  diagnostico: string;
  causa: string;
  solucion: string;
  prevencion: string;
}
const { sintoma, diagnostico, causa, solucion, prevencion } = Astro.props;
---
<div class="my-4 rounded border border-red-300 p-4">
  <p class="font-semibold mb-2">Código defectuoso</p>
  <slot />
  <p class="mt-3"><span class="font-semibold">Síntoma:</span> {sintoma}</p>
  <details class="mt-3 rounded border border-gray-300 p-3">
    <summary class="cursor-pointer font-semibold">Diagnóstico, causa y solución</summary>
    <div class="mt-2 space-y-1">
      <p><span class="font-semibold">Diagnóstico:</span> {diagnostico}</p>
      <p><span class="font-semibold">Causa raíz:</span> {causa}</p>
      <p><span class="font-semibold">Solución:</span> {solucion}</p>
      <p><span class="font-semibold">Prevención:</span> {prevencion}</p>
    </div>
  </details>
</div>
```

- [ ] **Step 4: Run to verify it passes**

```bash
npx vitest run src/components/ErrorChallenge.test.ts
```

Expected: 1 passed.

- [ ] **Step 5: Write the failing test for Comparativa**

Create `site/src/components/Comparativa.test.ts`:

```typescript
import { experimental_AstroContainer as AstroContainer } from 'astro/container';
import { expect, test } from 'vitest';
import Comparativa from './Comparativa.astro';

test('renders header columns and every row cell', async () => {
  const container = await AstroContainer.create();
  const result = await container.renderToString(Comparativa, {
    props: {
      columnas: ['Concepto', 'Qué es', 'Cuándo usarlo'],
      filas: [
        ['ArrayList', 'Lista respaldada por array', 'Acceso frecuente por índice'],
        ['LinkedList', 'Lista doblemente enlazada', 'Inserciones/borrados frecuentes en los extremos'],
      ],
    },
  });

  expect(result).toContain('Concepto');
  expect(result).toContain('LinkedList');
  expect(result).toContain('Inserciones/borrados frecuentes en los extremos');
});
```

- [ ] **Step 6: Run to verify it fails**

```bash
npx vitest run src/components/Comparativa.test.ts
```

Expected: FAIL — module not found.

- [ ] **Step 7: Implement Comparativa**

Create `site/src/components/Comparativa.astro`:

```astro
---
interface Props {
  columnas: string[];
  filas: string[][];
}
const { columnas, filas } = Astro.props;
---
<div class="my-4 overflow-x-auto">
  <table class="w-full border-collapse text-sm">
    <thead>
      <tr>
        {columnas.map((c) => <th class="border border-gray-300 bg-gray-100 px-3 py-2 text-left">{c}</th>)}
      </tr>
    </thead>
    <tbody>
      {filas.map((fila) => (
        <tr>
          {fila.map((celda) => <td class="border border-gray-300 px-3 py-2">{celda}</td>)}
        </tr>
      ))}
    </tbody>
  </table>
</div>
```

- [ ] **Step 8: Run to verify it passes**

```bash
npx vitest run src/components/Comparativa.test.ts
```

Expected: 1 passed.

- [ ] **Step 9: Commit**

```bash
cd /home/senpai/cursodejava
git add site/
git commit -m "feat(site): add ErrorChallenge and Comparativa components"
```

---

### Task 9: Progress catalog and dashboard

**Files:**
- Create: `site/progress.json`
- Create: `site/src/lib/progreso.ts`
- Create: `site/src/lib/progreso.test.ts`
- Create: `site/src/pages/progreso.astro`

**Interfaces:**
- Consumes: `leerCompletadas` from `site/src/lib/progresoStorage.ts` (Task 7).
- Produces: `calcularResumen(catalogo: CatalogoEntry[], completadas: string[]): ResumenProgreso` where `CatalogoEntry = { id: string; etapa: number; leccion: number; titulo: string; estado: 'publicada' }` and `ResumenProgreso = { totalPublicadas: number; totalCompletadas: number; porcentaje: number }`.

- [ ] **Step 1: Write the failing test**

Create `site/src/lib/progreso.test.ts`:

```typescript
import { describe, test, expect } from 'vitest';
import { calcularResumen, type CatalogoEntry } from './progreso';

const catalogo: CatalogoEntry[] = [
  { id: 'etapa-00/leccion-01', etapa: 0, leccion: 1, titulo: 'A', estado: 'publicada' },
  { id: 'etapa-00/leccion-02', etapa: 0, leccion: 2, titulo: 'B', estado: 'publicada' },
];

describe('calcularResumen', () => {
  test('computes counts and percentage', () => {
    const resumen = calcularResumen(catalogo, ['etapa-00/leccion-01']);
    expect(resumen.totalPublicadas).toBe(2);
    expect(resumen.totalCompletadas).toBe(1);
    expect(resumen.porcentaje).toBe(50);
  });

  test('returns 0% when nothing is completed and no publications', () => {
    const resumen = calcularResumen([], []);
    expect(resumen.porcentaje).toBe(0);
  });

  test('ignores completed ids that are not in the catalog', () => {
    const resumen = calcularResumen(catalogo, ['etapa-00/leccion-01', 'no-existe']);
    expect(resumen.totalCompletadas).toBe(1);
  });
});
```

- [ ] **Step 2: Run to verify it fails**

```bash
npx vitest run src/lib/progreso.test.ts
```

Expected: FAIL — module not found.

- [ ] **Step 3: Implement**

Create `site/src/lib/progreso.ts`:

```typescript
export interface CatalogoEntry {
  id: string;
  etapa: number;
  leccion: number;
  titulo: string;
  estado: 'publicada';
}

export interface ResumenProgreso {
  totalPublicadas: number;
  totalCompletadas: number;
  porcentaje: number;
}

export function calcularResumen(catalogo: CatalogoEntry[], completadas: string[]): ResumenProgreso {
  const idsCatalogo = new Set(catalogo.map((c) => c.id));
  const totalCompletadas = completadas.filter((id) => idsCatalogo.has(id)).length;
  const totalPublicadas = catalogo.length;
  const porcentaje = totalPublicadas === 0 ? 0 : Math.round((totalCompletadas / totalPublicadas) * 100);
  return { totalPublicadas, totalCompletadas, porcentaje };
}
```

- [ ] **Step 4: Run to verify it passes**

```bash
npx vitest run src/lib/progreso.test.ts
```

Expected: 3 passed.

- [ ] **Step 5: Create the catalog**

Create `site/progress.json`:

```json
{
  "catalogo": [
    {
      "id": "etapa-00-como-funciona-java/leccion-01-que-ocurre-cuando-ejecutas-java",
      "etapa": 0,
      "leccion": 1,
      "titulo": "Qué ocurre cuando ejecutas un programa Java",
      "estado": "publicada"
    }
  ]
}
```

- [ ] **Step 6: Dashboard page**

Create `site/src/pages/progreso.astro`:

```astro
---
import '../styles/global.css';
import progressData from '../../progress.json';
---
<html lang="es">
  <head>
    <meta charset="utf-8" />
    <title>Tu progreso — cursodejava</title>
  </head>
  <body class="mx-auto max-w-3xl px-4 py-8">
    <h1 class="text-3xl font-bold mb-6">Tu progreso</h1>
    <div id="resumen" class="mb-6 rounded border border-gray-300 p-4"></div>
    <ul id="lista" class="space-y-2"></ul>

    <script define:vars={{ catalogo: progressData.catalogo }}>
      import { calcularResumen } from '../lib/progreso';
      import { leerCompletadas } from '../lib/progresoStorage';

      const completadas = leerCompletadas();
      const resumen = calcularResumen(catalogo, completadas);

      document.getElementById('resumen').textContent =
        `${resumen.totalCompletadas} / ${resumen.totalPublicadas} lecciones completadas (${resumen.porcentaje}%)`;

      const lista = document.getElementById('lista');
      catalogo.forEach((entrada) => {
        const li = document.createElement('li');
        const hecha = completadas.includes(entrada.id);
        li.textContent = `${hecha ? '✅' : '⬜'} Etapa ${entrada.etapa} — Lección ${entrada.leccion}: ${entrada.titulo}`;
        lista.appendChild(li);
      });
    </script>
  </body>
</html>
```

- [ ] **Step 7: Verify the page builds**

```bash
npm run build
test -f dist/progreso/index.html && echo "PROGRESO OK"
```

Expected: `PROGRESO OK`.

- [ ] **Step 8: Commit**

```bash
cd /home/senpai/cursodejava
git add site/
git commit -m "feat(site): add progress catalog and dashboard page"
```

---

### Task 10: Etapa 0 / Lección 1 — contenido real (corte vertical completo)

**Files:**
- Create: `proyecto/etapa-00/HolaMundo.java`
- Modify: `site/src/content/lecciones/etapa-00-como-funciona-java/leccion-01-que-ocurre-cuando-ejecutas-java.mdx`

**Interfaces:**
- Consumes: `Reveal`, `Quiz`, `SelfCheck`, `Comparativa` components (Tasks 5–8); `LeccionLayout` route (Task 4).

- [ ] **Step 1: Write and verify the real Java code**

Create `proyecto/etapa-00/HolaMundo.java`:

```java
public class HolaMundo {
    public static void main(String[] args) {
        System.out.println("Sistema de Gestion de Pedidos e Inventario");
    }
}
```

Compile and run it for real, capture the exact output:

```bash
cd /home/senpai/cursodejava/proyecto/etapa-00
javac HolaMundo.java
java HolaMundo
```

Expected output (copy verbatim into the lesson in Step 2):
```
Sistema de Gestion de Pedidos e Inventario
```

- [ ] **Step 2: Write the full lesson content**

Replace the stub body of `site/src/content/lecciones/etapa-00-como-funciona-java/leccion-01-que-ocurre-cuando-ejecutas-java.mdx` (keep the existing frontmatter) with the full 28-point template from the master prompt: qué vas a aprender, por qué, prerrequisitos, recupera lo aprendido, problema, modelo mental (ASCII: código fuente → javac → bytecode .class → JVM → SO), explicación sencilla, explicación técnica, ejemplo mínimo (`HolaMundo.java` above, with a `<Reveal titulo="Resultado">` showing the real captured output), explicación paso a paso, ejemplo aplicado al proyecto, error común (`<ErrorChallenge>` — e.g. `java HolaMundo.java` vs `java HolaMundo` confusion, or a `public class` name not matching the filename), ejercicio guiado, ejercicio independiente, transferencia, `<Quiz>` de comprobación, `<Comparativa>` (`javac` vs `java`), resumen, modelo mental final, conexión, próximo paso, y Fuentes (citando dev.java/OpenJDK). Import the components at the top of the MDX frontmatter block:

```mdx
import Reveal from '../../../components/Reveal.astro';
import Quiz from '../../../components/Quiz.astro';
import SelfCheck from '../../../components/SelfCheck.astro';
import ErrorChallenge from '../../../components/ErrorChallenge.astro';
import Comparativa from '../../../components/Comparativa.astro';
```

- [ ] **Step 3: Verify the build**

```bash
cd /home/senpai/cursodejava/site
npm run build
```

Expected: build succeeds, no Zod schema errors, no MDX/component import errors.

- [ ] **Step 4: Commit**

```bash
cd /home/senpai/cursodejava
git add proyecto/ site/
git commit -m "content(site): publish Etapa 0 / Lección 1 end to end"
```

---

### Task 11: Manual browser verification

Not a code task — a verification gate before calling the scaffold done. Use the **webapp-testing** skill (Playwright) or manual browser check:

- [ ] **Step 1: Start the dev server**

```bash
cd /home/senpai/cursodejava/site
npm run dev
```

- [ ] **Step 2: Verify in a real browser (or via Playwright through webapp-testing)**
  - `/lecciones/etapa-00-como-funciona-java/leccion-01-que-ocurre-cuando-ejecutas-java` loads, shows the full lesson.
  - Clicking a `<Reveal>` summary expands the hidden content.
  - Clicking a `<Quiz>` option shows the correct per-option feedback text (both a right and a wrong option).
  - Clicking a `<SelfCheck>` level button, then reloading `/progreso`, shows the lesson marked completed and the percentage updated.
  - `npm run build && npm run preview` also serves correctly (confirms the static output, not just dev mode, works).

- [ ] **Step 3: Fix anything broken, re-verify, then report to the user** what was published and where to look.

---

## Self-Review Notes

- **Spec coverage:** §4.1 stack (Astro/Tailwind/no-framework) → Tasks 1–2. §4.2 content structure/schema → Task 3–4. §4.3 components → Tasks 5–8. §5 content sourcing (verified code) → Task 10. §6 progress (progress.json + localStorage) → Tasks 7, 9. §7 stack table — already verified and recorded in the design spec, not re-verified here. §8 `proyecto/` → Task 10 (explicitly *not* Maven yet, matching pedagogical sequence — corrected from the spec's implicit assumption).
- **Correction from spec:** design spec §4.2 said `site/src/content/config.ts`; current Astro puts this at `site/src/content.config.ts` (verified against live docs during planning). This plan uses the correct current path.
- **Type consistency checked:** `Leccion`/`leccionSchema` (Task 3) → consumed by `getCollection('lecciones')` in Task 4. `QuizOpcion`/`evaluarRespuesta` (Task 6) used identically in both the pure-function test and the component. `leerCompletadas`/`guardarSelfCheck`/`marcarCompletada` (Task 7) → consumed by `SelfCheck.astro` (Task 7) and `progreso.astro` (Task 9) with matching names.
