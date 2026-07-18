export interface CatalogoEntry {
  id: string;
  etapa: number;
  leccion: number;
  titulo: string;
  estado: 'publicada';
}

export interface ResumenProgreso {
  totalPublicadas: number;
  totalCompletadas: number;
  porcentaje: number;
}

export function calcularResumen(catalogo: CatalogoEntry[], completadas: string[]): ResumenProgreso {
  const idsCatalogo = new Set(catalogo.map((c) => c.id));
  const totalCompletadas = completadas.filter((id) => idsCatalogo.has(id)).length;
  const totalPublicadas = catalogo.length;
  const porcentaje = totalPublicadas === 0 ? 0 : Math.round((totalCompletadas / totalPublicadas) * 100);
  return { totalPublicadas, totalCompletadas, porcentaje };
}
