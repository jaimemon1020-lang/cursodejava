export type NivelDominio = 'DOMINADO' | 'PARCIAL' | 'INSUFICIENTE';

const SELFCHECK_PREFIX = 'cursodejava:selfcheck:';
const COMPLETADAS_KEY = 'cursodejava:completadas';

export function guardarSelfCheck(leccionId: string, nivel: NivelDominio): void {
  localStorage.setItem(SELFCHECK_PREFIX + leccionId, nivel);
}

export function leerSelfCheck(leccionId: string): NivelDominio | null {
  return localStorage.getItem(SELFCHECK_PREFIX + leccionId) as NivelDominio | null;
}

export function marcarCompletada(leccionId: string): void {
  const actuales = leerCompletadas();
  if (!actuales.includes(leccionId)) {
    localStorage.setItem(COMPLETADAS_KEY, JSON.stringify([...actuales, leccionId]));
  }
}

export function leerCompletadas(): string[] {
  const raw = localStorage.getItem(COMPLETADAS_KEY);
  return raw ? JSON.parse(raw) : [];
}
