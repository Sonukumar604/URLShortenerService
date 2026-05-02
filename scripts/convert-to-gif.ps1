<#
Converts frames in ./scripts/frames (frame001.png, frame002.png, ...) into demo.gif using ffmpeg.
Usage: Open PowerShell in the repo root and run: `./scripts/convert-to-gif.ps1`
#>

$framesDir = Join-Path $PSScriptRoot 'frames'
Set-Location (Join-Path $PSScriptRoot '..')

if (-not (Test-Path $framesDir)) {
    Write-Error "Frames directory not found: $framesDir`nRun scripts/capture-frames.js first to generate frames."
    exit 1
}

if (-not (Get-Command ffmpeg -ErrorAction SilentlyContinue)) {
    Write-Error "ffmpeg not found in PATH. Install ffmpeg and re-run this script."
    exit 2
}

$oldPwd = Get-Location
Set-Location $framesDir

& ffmpeg -y -framerate 12 -i frame%03d.png -vf "scale=800:-1:flags=lanczos,palettegen" palette.png
& ffmpeg -y -framerate 12 -i frame%03d.png -i palette.png -filter_complex "scale=800:-1:flags=lanczos[x];[x][1:v]paletteuse" ..\demo.gif

Write-Output "Created demo.gif in repository root."
Set-Location $oldPwd
