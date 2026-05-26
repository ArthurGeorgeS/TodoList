# Compila e executa Conexao usando libs\mysql-connector-java-latest.jar
Set-Location -LiteralPath "$PSScriptRoot\.."
$latest = Join-Path $PWD.Path 'libs\mysql-connector-java-latest.jar'
if (-not (Test-Path $latest)) {
    Write-Host 'JAR latest não encontrado em libs; execute o download primeiro.'
    exit 1
}
if (-not (Test-Path out)) { New-Item -ItemType Directory out | Out-Null }
& javac -cp ".;$latest" -d out src\Conexao.java
if ($LASTEXITCODE -eq 0) {
    & java -cp "out;$latest" Conexao
} else {
    Write-Host "Falha na compilação (javac exit code: $LASTEXITCODE)"
}
