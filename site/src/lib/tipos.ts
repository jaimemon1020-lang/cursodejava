export interface LeccionLink {
  id: string;
  num: number;
  titulo: string;
}

export interface EtapaGrupo {
  num: number;
  titulo: string;
  lecciones: LeccionLink[];
}
