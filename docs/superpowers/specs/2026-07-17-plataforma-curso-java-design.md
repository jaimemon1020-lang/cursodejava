# Diseño: Plataforma del curso interactivo de Java → Spring Boot → Arquitectura Hexagonal

Fecha: 2026-07-17
Estado: aprobado por el usuario, pendiente de plan de implementación

## 1. Contexto y objetivo

El usuario quiere recorrer una ruta completa de aprendizaje (Java desde cero → POO → Java
intermedio/funcional/avanzado → SOLID/patrones → Spring → Spring Boot → APIs REST →
JPA/Hibernate → testing → seguridad → Arquitectura Hexagonal → Docker/producción), siguiendo
un motor pedagógico estricto (Backward Design, Bloom, scaffolding, worked examples, retrieval
practice, repaso espaciado, interleaving, UDL, mastery learning) descrito en un prompt maestro
provisto por el usuario. Ese prompt maestro es la fuente de verdad pedagógica y de contenido;
este documento diseña únicamente **la plataforma** donde ese contenido va a vivir.

Decisión explícita del usuario: la tutoría interactiva (evaluar respuestas, dar feedback,
retrieval practice, autoevaluación de dominio) **no ocurre en este chat**. Debe ocurrir dentro
de la plataforma. Este chat pasa a ser el canal de construcción del proyecto (yo escribo código
y contenido, reporto avance), no el canal de enseñanza.

## 2. No-objetivos

- No hay motor de IA en vivo dentro de la plataforma (se descartó explícitamente: implicaría
  backend, API key de Anthropic a cargo del usuario, y manejo de conversación — fuera de
  alcance).
- No hay ejecución de código Java embebida en el sitio (sandbox/Judge0/etc.). El usuario
  compila y corre el proyecto Java en su propio entorno.
- No hay cuentas de usuario ni multi-usuario. Es una plataforma personal de un solo usuario,
  corrida localmente.
- No se traduce contenido desde tutoriales existentes. El contenido se redacta original en
  español, verificado contra documentación oficial.

## 3. Arquitectura general

Monorepo con dos carpetas de primer nivel:

```
cursodejava/
├── site/              Plataforma (Astro) — el curso interactivo
├── proyecto/           Sistema de Gestión de Pedidos e Inventario (Java/Maven)
├── docs/superpowers/specs/   Specs de diseño (este archivo y futuros)
└── README.md
```

`site/` y `proyecto/` son independientes entre sí (el sitio referencia/embebe snippets de
`proyecto/`, pero no lo importa como dependencia de build). Cada uno puede evolucionar y
commitearse por separado.

## 4. Plataforma (`site/`)

### 4.1 Stack técnico

| Decisión | Elección | Motivo |
|---|---|---|
| Framework | Astro | Content collections nativas para markdown/MDX, islas interactivas opcionales, salida estática, buen soporte de resaltado de código (Shiki). |
| Estilos | Tailwind CSS | Setup rápido, consistente, fácil de mantener por mí sin diseño a medida por página. |
| Interactividad | HTML nativo (`<details>`) + JS vanilla en islas puntuales | No se necesita React/Vue/Svelte: la mecánica pedagógica (revelar respuesta, quiz, self-check) no requiere un framework de UI completo. Menos dependencias. |
| Contenido | Markdown/MDX vía Content Collections de Astro | Un archivo por lección, tipado con schema (Zod) para frontmatter consistente. |
| Progreso | `site/progress.json` (repo) + `localStorage` (navegador) | Ver sección 6. |
| Hosting | Servidor de desarrollo local (`astro dev`) inicialmente; build estático (`astro build`) disponible si más adelante se quiere desplegar | No se decidió despliegue remoto; se puede agregar después sin rediseño (site estático). |

### 4.2 Estructura de contenido

```
site/src/content/lecciones/
  etapa-00-como-funciona-java/
    leccion-01-que-ocurre-cuando-ejecutas-java.mdx
  etapa-01-java-basico/
    leccion-01-...mdx
    leccion-02-...mdx
  ...
```

Cada lección es un `.mdx` con frontmatter (validado por schema en `site/src/content/config.ts`):

```ts
{
  etapa: number,          // 0..25 según el mapa del prompt maestro
  etapaTitulo: string,
  leccion: number,        // orden dentro de la etapa
  titulo: string,
  objetivo: string,       // "Después de esta lección seré capaz de..."
  prerrequisitos: string[],   // slugs de lecciones previas
  version_proyecto: string,   // ej. "v3 — POO y encapsulamiento", referencia a proyecto/
}
```

El body del `.mdx` sigue la plantilla de 28 puntos del prompt maestro (qué vas a aprender, por
qué, prerrequisitos, recupera lo aprendido, problema, modelo mental, explicación sencilla y
técnica, ejemplo mínimo, predice/resultado, ejemplo aplicado al proyecto, error común,
ejercicios trabajado/guiado/independiente, transferencia, comprobación, resumen, fuentes, etc.)
usando los componentes descritos en 4.3. **Contenido completo, no resumido** — nada queda solo
en el chat.

Idioma: español técnico neutro/profesional (no rioplatense/voseo — ese registro se reserva
para el trato conversacional en el chat de construcción).

### 4.3 Componentes interactivos

Todos viven en `site/src/components/`, sin dependencias externas más allá de Astro:

- **`<Reveal title="...">`** — envoltorio sobre `<details>/<summary>` nativo. Usado para:
  resultado de un "predice qué pasará", solución de un ejercicio, hints progresivos (varios
  `<Reveal>` anidados o secuenciales), diagnóstico/causa/fix de un error.
- **`<Quiz pregunta="..." opciones={[...]} />`** — opción múltiple con feedback específico por
  opción elegida (no solo correcto/incorrecto: explica por qué cada distractor está mal). Isla
  con JS vanilla mínimo (sin framework), estado en memoria del componente.
- **`<SelfCheck criterios={[...]} />`** — autoevaluación de dominio (DOMINADO / PARCIAL /
  INSUFICIENTE) al cierre de cada concepto fundamental, con las preguntas de metacognición del
  prompt maestro (¿qué puedo explicar ahora?, ¿qué debo repasar?). Guarda el resultado en
  `localStorage`.
- **`<ErrorChallenge codigo="..." sintoma="..." />`** — código defectuoso + pregunta ("¿qué
  creés que está mal?") + `<Reveal>` con diagnóstico, causa raíz, solución y prevención.
- **`<Comparativa filas={[...]} />`** — tabla de comparación (Concepto / Qué es /
  Responsabilidad / Cuándo usarlo / Diferencia), para las comparaciones obligatorias del
  prompt maestro.

Estos componentes son plantillas de presentación: **el contenido de cada rama (qué dice cada
feedback, cada hint, cada solución) lo escribo yo al redactar la lección**, no se genera en
runtime.

### 4.4 Diagramas

ASCII art dentro de bloques de código (tal como pide el prompt maestro), no se agrega Mermaid
ni librerías de diagramas para mantener el stack mínimo.

## 5. Contenido: cómo se obtiene y se verifica

1. Antes de escribir una lección con carga técnica nueva (una API, anotación, versión), busco
   la documentación oficial correspondiente (dev.java, docs.spring.io, Jakarta specs, etc.) —
   mismo proceso que ya se usó para verificar el stack (sección 7).
2. El texto pedagógico (explicaciones, analogías, modelos mentales, preguntas) lo redacto yo
   directamente en español, aplicando el motor pedagógico del prompt maestro. No es traducción.
3. Todo ejemplo de código que aparece en una lección proviene de `proyecto/`, donde se escribe,
   compila y ejecuta antes de copiarse a la lección — no hay snippets sin verificar.
4. Cada lección cierra con una sección "Fuentes" citando la documentación oficial usada.

## 6. Seguimiento de progreso

No hay backend ni cuentas, así que el progreso se divide en dos capas:

- **`site/progress.json`** (versionado en git, lo mantengo yo): catálogo de lecciones
  publicadas — etapa, lección, título, estado (`publicada`/`pendiente`), fecha. Es la fuente de
  verdad de **qué existe** en el sitio, y también lo que me permite retomar entre sesiones de
  chat sabiendo hasta dónde construí.
- **`localStorage`** (navegador del usuario): marca de "completada" por el usuario en cada
  lección (checkbox) y resultados de `<SelfCheck>`. Es la fuente de verdad de **qué hizo** el
  usuario. No se sincroniza a git — es de un solo usuario, un solo navegador, uso local.

Una página `site/src/pages/progreso.astro` lee `progress.json` (build-time) y `localStorage`
(client-side) para mostrar un dashboard: etapas, lecciones publicadas, y cuáles marcaste como
completadas.

## 7. Stack tecnológico del curso (Java/Spring), verificado 2026-07-17

| Tecnología | Versión seleccionada | Motivo |
|---|---|---|
| Java | 21 (LTS) | LTS con ~2.5 años de ecosistema maduro: máxima disponibilidad de documentación, tutoriales y compatibilidad. Java 25 ya es LTS (sept. 2025) pero con ~10 meses de adopción; se prioriza estabilidad y material de aprendizaje sobre usar la versión más nueva. |
| Spring Boot | 4.1.x | Release soportado actualmente (soporte hasta jul. 2027), Jakarta EE 11, primera clase de soporte para Java 17–26 (incluye 21). |
| Spring Framework | 7.0.x | Requerido por Spring Boot 4.1. |
| Maven | 3.9.x | Versión estable recomendada actual. |
| PostgreSQL | 18.x | Última versión estable mayor. |
| Testing | JUnit 6, Mockito 5, Testcontainers, Flyway | Stack de testing vigente para proyectos Java/Spring en 2026. |

Estas versiones se re-verifican puntualmente cuando la lección correspondiente lo requiera
(regla del prompt maestro: nunca inventar versiones).

## 8. Proyecto Java (`proyecto/`)

Un único proyecto Maven que evoluciona in-place a través de las 25 versiones descritas en el
prompt maestro (consola → POO → Collections → ... → Arquitectura Hexagonal → Docker). No se
recrea desde cero en cada etapa. Cada avance relevante es un commit; en etapas clave puede
llevar tag (ej. `v10-spring-di`). Las lecciones del sitio referencian la versión del proyecto
vigente en su frontmatter (`version_proyecto`).

## 9. Flujo de trabajo de construcción

1. Se decide (en este chat) qué lección/módulo toca construir.
2. Investigo fuentes oficiales si hay contenido técnico nuevo.
3. Escribo/actualizo código en `proyecto/`, lo compilo/corro.
4. Escribo el `.mdx` de la lección en `site/`, con componentes interactivos poblados.
5. Actualizo `site/progress.json`.
6. Commit local (y push si el usuario lo pide explícitamente).
7. Reporto en el chat qué se publicó y dónde revisarlo.

## 10. Alcance de este documento vs. próximos pasos

Este spec cubre la plataforma (estructura, componentes, progreso, flujo). No cubre todavía:
- El scaffolding inicial concreto (`npm create astro`, config de Tailwind, schema de content
  collections, layout base) — eso es el siguiente plan de implementación.
- El detalle pedagógico de cada etapa/lección — eso lo define el prompt maestro del usuario y
  se ejecuta lección a lección durante la construcción, no en este documento.
- Despliegue remoto del sitio (no solicitado; el sitio corre local por ahora).
