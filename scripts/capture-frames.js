const { chromium } = require('playwright');
const fs = require('fs');

(async () => {
  const outDir = './scripts/frames';
  fs.mkdirSync(outDir, { recursive: true });

  const browser = await chromium.launch();
  const page = await browser.newPage({ viewport: { width: 1200, height: 800 } });
  const base = process.env.URL || 'http://localhost:8081';

  console.log('Opening', base);
  await page.goto(base, { waitUntil: 'networkidle' });

  // initial home page frame
  await page.screenshot({ path: `${outDir}/frame001.png` });

  // fill the form (assumes input[name="url"] and a button labeled Shorten URL)
  try {
    await page.fill('input[name="url"]', 'https://github.com');
  } catch (e) {
    // fallback: try generic input
    const input = await page.$('input');
    if (input) await input.fill('https://github.com');
  }

  // click the shorten button
  try {
    await page.click('button:has-text("Shorten URL")');
  } catch (e) {
    // try any button
    const btn = await page.$('button');
    if (btn) await btn.click();
  }

  // wait for the result page (or a short code element)
  await page.waitForTimeout(1000);

  // capture a sequence of frames over ~20 seconds (12 frames, 2s apart)
  const frames = 12;
  for (let i = 0; i < frames; i++) {
    const idx = (i + 2).toString().padStart(3, '0');
    await page.waitForTimeout(2000);
    await page.screenshot({ path: `${outDir}/frame${idx}.png` });
    console.log('Saved frame', idx);
  }

  await browser.close();
  console.log('Done — frames saved to', outDir);
})();
