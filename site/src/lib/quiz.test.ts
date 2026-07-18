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
