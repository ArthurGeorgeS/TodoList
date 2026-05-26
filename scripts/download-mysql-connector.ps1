param(
    [string]$Version = "8.0.33",
    [string]$OutputDir = "..\libs"
)

$baseUrl = "https://repo1.maven.org/maven2/mysql/mysql-connector-java/$Version"
$jarName = "mysql-connector-java-$Version.jar"
$jarUrl = "$baseUrl/$jarName"

$fullOutputDir = Join-Path $PSScriptRoot $OutputDir
if (-not (Test-Path $fullOutputDir)) { New-Item -ItemType Directory -Path $fullOutputDir | Out-Null }

$dest = Join-Path $fullOutputDir $jarName
Write-Host "Baixando $jarUrl para $dest ..."
try {
    Invoke-WebRequest -Uri $jarUrl -OutFile $dest -UseBasicParsing
    Write-Host "Download concluído: $dest"
} catch {
    Write-Error "Falha no download: $_"
}
