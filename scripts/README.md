Capture demo frames and convert to GIF
===================================

This folder contains a Playwright script to capture a sequence of PNG frames from the running app and a PowerShell helper to convert those frames into a GIF using `ffmpeg`.

Prerequisites
- Node.js (>=16)
- `npm` or `pnpm`
- `playwright` package
- `ffmpeg` in PATH (for `convert-to-gif.ps1`)

Install Playwright (one-time):

```powershell
npm init -y
npm install playwright
# optionally install browsers (Playwright will download browsers on first run or run: npx playwright install)
npx playwright install
```

Capture frames
---------------
Run the capture script while your Spring Boot app is running on `http://localhost:8081`:

```powershell
node ./scripts/capture-frames.js
```

Frames will be written to `./scripts/frames/frame001.png`, `frame002.png`, ...

Convert to GIF (requires `ffmpeg`)
----------------------------------
From the repo root run:

```powershell
.\scripts\convert-to-gif.ps1
```

Output: `demo.gif` in the repository root.

Notes
- If your app runs on a different port or host, set the `URL` environment variable before running the capture script.
  ```powershell
  $env:URL='http://localhost:8081'
  node ./scripts/capture-frames.js
  ```
- If you prefer manual screenshots, place them in `./scripts/frames` and run the conversion script.
