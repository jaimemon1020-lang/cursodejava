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
