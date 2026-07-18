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
