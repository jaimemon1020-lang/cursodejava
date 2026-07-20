import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';
import tailwindcss from '@tailwindcss/vite';

const etapas = [
  { label: '00 — Cómo funciona Java', dir: 'etapa-00-como-funciona-java' },
  { label: '01 — Java Básico', dir: 'etapa-01-java-basico' },
  { label: '02 — POO', dir: 'etapa-02-poo' },
  { label: '03 — Modelado OO', dir: 'etapa-03-modelado-oo' },
  { label: '04 — Collections', dir: 'etapa-04-collections' },
  { label: '05 — Prog. Funcional', dir: 'etapa-05-funcional' },
  { label: '06 — Java Avanzado', dir: 'etapa-06-avanzado' },
  { label: '07 — Clean Code & SOLID', dir: 'etapa-07-clean-code-solid' },
  { label: '08 — Patrones de Diseño', dir: 'etapa-08-patrones' },
  { label: '09 — Maven', dir: 'etapa-09-maven' },
  { label: '10 — Spring Framework', dir: 'etapa-10-spring-framework' },
  { label: '11 — Spring Boot', dir: 'etapa-11-spring-boot' },
  { label: '12 — HTTP REST', dir: 'etapa-12-http-rest' },
  { label: '13 — DTOs & Mapping', dir: 'etapa-13-dtos-mapping' },
  { label: '14 — Validación', dir: 'etapa-14-validacion-manejo-errores' },
  { label: '15 — Bases de Datos', dir: 'etapa-15-bases-de-datos' },
  { label: '16 — Migraciones', dir: 'etapa-16-migraciones' },
  { label: '17 — Testing', dir: 'etapa-17-testing' },
  { label: '18 — Seguridad', dir: 'etapa-18-seguridad' },
  { label: '19 — Fundamentos', dir: 'etapa-19-fundamentos-arquitectura' },
  { label: '20 — Hexagonal', dir: 'etapa-20-arquitectura-hexagonal' },
];

export default defineConfig({
  integrations: [
    starlight({
      title: 'Curso de Java',
      description: 'De cero a Spring Boot + Arquitectura Hexagonal en 20 etapas',
      customCss: ['./src/styles/starlight.css'],
      sidebar: [
        { label: 'Inicio', link: '/' },
        ...etapas.map(({ label, dir }) => ({
          label: `Etapa ${label}`,
          collapsed: true,
          items: [{ autogenerate: { directory: dir } }],
        })),
      ],
      expressiveCode: {
        themes: ['github-light', 'github-dark'],
        styleOverrides: {
          borderRadius: '0.5rem',
          frames: { frameBoxShadowCssValue: 'none' },
        },
      },
      tableOfContents: {
        minHeadingLevel: 2,
        maxHeadingLevel: 3,
      },
      pagination: true,
      head: [
        {
          tag: 'link',
          attrs: { rel: 'preconnect', href: 'https://fonts.googleapis.com' },
        },
        {
          tag: 'link',
          attrs: { rel: 'preconnect', href: 'https://fonts.gstatic.com', crossorigin: 'anonymous' },
        },
        {
          tag: 'link',
          attrs: {
            rel: 'stylesheet',
            href: 'https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&family=JetBrains+Mono:wght@400;500;600&display=swap',
          },
        },
      ],
    }),
  ],
  vite: {
    plugins: [tailwindcss()],
  },
});
