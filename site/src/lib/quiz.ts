export interface QuizOpcion {
  texto: string;
  correcta: boolean;
  feedback: string;
}

export function evaluarRespuesta(opciones: QuizOpcion[], indiceSeleccionado: number) {
  const opcion = opciones[indiceSeleccionado];
  if (!opcion) {
    throw new Error(`Índice de opción fuera de rango: ${indiceSeleccionado}`);
  }
  return { correcta: opcion.correcta, feedback: opcion.feedback };
}
