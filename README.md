# Acervo da Bíblia — leitor EPUB Android

Projeto inicial para construir o APK pelo GitHub Actions, sem Android Studio no celular.

## O que esta versão tenta entregar
- Importar um EPUB pelo seletor de arquivos do Android.
- Ler capítulos com epub.js.
- Alternar tema claro/escuro.
- Aumentar/diminuir a fonte.
- Salvar biblioteca e posição de leitura no armazenamento local do WebView.

## Como gerar o APK pelo celular
1. Crie um repositório GitHub chamado `acervo-da-biblia`.
2. Envie todos os arquivos e pastas deste projeto para a raiz do repositório.
3. Abra a aba **Actions** do repositório e habilite Actions se o GitHub solicitar.
4. Na workflow **Build Acervo da Bíblia APK**, toque em **Run workflow**. Também inicia automaticamente quando você envia alterações para `main`.
5. Aguarde o job ficar verde. Abra a execução concluída e, em **Artifacts**, baixe `Acervo-da-Biblia-debug`.
6. Extraia o ZIP baixado e instale `app-debug.apk` no Android. Se solicitado, autorize a instalação para o navegador/gerenciador de arquivos.

## Importante
- Este é um protótipo inicial; a primeira compilação precisa ser testada no aparelho.
- O leitor usa epub.js e JSZip via CDN; por isso, a abertura inicial do leitor precisa de internet. A leitura offline completa exige incluir essas bibliotecas localmente no projeto.
- Os dados da biblioteca ficam no armazenamento do WebView. Desinstalar o app ou limpar os dados pode apagá-los.
- O APK `debug` é para testes, não é uma versão assinada para publicação na Play Store.
- Alguns EPUBs protegidos por DRM não abrirão.
