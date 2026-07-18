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
