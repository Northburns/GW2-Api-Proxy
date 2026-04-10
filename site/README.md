
https://kvision.gitbook.io/kvision-guide/1.-getting-started-1/development-workflow

````shell
.\gradlew :site:generate
.\gradlew -t :site:run
````

I took a lot of inspiration from https://github.com/rjaros/kvision-realworld-example-app/tree/master

### Notes

Chrome disable CORS checking (for local testing), powershell:

```ps1
Start-Process "C:\Program Files (x86)\Google\Chrome\Application\chrome.exe" `
    -ArgumentList "--disable-web-security", `
    "--user-data-dir=""C:\temp1\chrome"""
```
