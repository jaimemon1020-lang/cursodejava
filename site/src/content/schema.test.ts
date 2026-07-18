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
