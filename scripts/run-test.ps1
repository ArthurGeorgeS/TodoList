# Script para baixar mysql-connector (várias versões), compilar e executar Conexao
Set-Location -LiteralPath "$PSScriptRoot\.."
$versions = @('8.0.33','8.0.32','8.0.30','8.0.28')
if (-not (Test-Path libs)) { New-Item -ItemType Directory libs | Out-Null }
$downloaded = $null
foreach ($v in $versions) {
    $url = "https://repo1.maven.org/maven2/mysql/mysql-connector-java/$v/mysql-connector-java-$v.jar"
    $dest = Join-Path $PWD.Path "libs\mysql-connector-java-$v.jar"
    Write-Host "Tentando $url"
    try {
        Invoke-WebRequest -Uri $url -OutFile $dest -UseBasicParsing -ErrorAction Stop
        Write-Host "Baixado $dest"
        $downloaded = $dest
        break
    } catch {
        Write-Host "Falha ao baixar $v"
    }
}

if (-not $downloaded) {
    Write-Host 'Não foi possível baixar nenhuma versão do connector.'
    exit 0
}

$latest = Join-Path $PWD.Path 'libs\mysql-connector-java-latest.jar'
Copy-Item -Force -Path $downloaded -Destination $latest
if (-not (Test-Path out)) { New-Item -ItemType Directory out | Out-Null }

# Compila e executa usando o jar mais recente
& javac -cp ".;$latest" -d out src\Conexao.java
if ($LASTEXITCODE -eq 0) {
    & java -cp "out;$latest" Conexao
} else {
    Write-Host "Falha na compilação. Saída do javac: $LASTEXITCODE"
}
