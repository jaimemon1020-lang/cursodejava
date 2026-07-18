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
