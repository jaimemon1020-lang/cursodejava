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
