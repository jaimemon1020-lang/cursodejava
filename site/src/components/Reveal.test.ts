import { experimental_AstroContainer as AstroContainer } from 'astro/container';
import { expect, test } from 'vitest';
import Reveal from './Reveal.astro';

test('renders a native details/summary with the given title and hidden content', async () => {
  const container = await AstroContainer.create();
  const result = await container.renderToString(Reveal, {
    props: { titulo: 'Ver resultado' },
    slots: { default: '<p>42</p>' },
  });

  expect(result).toContain('<details');
  expect(result).toContain('<summary');
  expect(result).toContain('Ver resultado');
  expect(result).toContain('<p>42</p>');
});
