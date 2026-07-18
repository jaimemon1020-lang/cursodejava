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
