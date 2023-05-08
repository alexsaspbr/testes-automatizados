#### Comandos basicos Docker

##### listar containers
```shell
docker container ls 
ou 
docker ps
```

##### listar todos containers (até os parados)
```shell
docker container ls -a
ou 
docker ps -a
```

##### listar id dos containers
```shell
docker container ls -q
ou 
docker ps -q
```

##### iniciar container pelo id
```shell
docker start <id>
```

##### parar container pelo id
```shell
docker stop <id>
```

##### parar containers pelos ids
```shell
docker stop $(docker container ls -q)
```

##### remover todos os containeres parados
```shell
docker container prune
```

#### Comando para gerar uma imagem Docker

>OBS: Tem que ter o Dockerfile

```shell
docker build -t alex-araujo/conta-bancaria .
```
