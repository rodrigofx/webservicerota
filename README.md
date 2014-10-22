Projeto Rota
========

Sobre  a linguaguem Grails

 Não precisa ficar declarando get/set metodos ,isto facilita o desenvolvimento e ganha em produtividade

 scalfo lding da linguguem ja gera o crud pronto a partir das classes

POSTMAN => Rest Client
 https://chrome.google.com/webstore/detail/postman-rest-client/fdmmgilgnpjigdojojpjoooidkmcomcm

salvar
 url: http://localhost:8080/webservicerota/ap1/v1/mapa method:post


 Exemplo

 {
    "nome": "Mapa1",
    "malhas": [
        {
            "origem": "A",
            "destino": "B",
            "distancia": 10
        },
        {
            "origem": "A",
            "destino": "C",
            "distancia": 20
        },
        {
            "origem": "B",
            "destino": "E",
            "distancia": 50
        },
		{
            "origem": "B",
            "destino": "D",
            "distancia": 15
        },
        {
            "origem": "C",
            "destino": "D",
            "distancia": 30
        },
        {
            "origem": "D",
            "destino": "E",
            "distancia": 30
        }
    ]
}


* Foi integrado Dijskstra algoritmo em groovy.

Ex: buscar caminho mais curto
http://localhost:8080/webservicerota/api/v1/mapa/1?pontoInicial=A&pontoFinal=D&autonomia=10&gasolina=2

Tem como resposta o mapa do com as rotas,caminho mais curto e o custo da viagem.

{
    "menorCaminho": "[A, B, D]",
    "custo": 3,
    "mapa": {
        "class": "webservicerota.Mapa",
        "id": 1,
        "malhas": [
            {
                "class": "webservicerota.Malha",
                "id": 1
            },
            {
                "class": "webservicerota.Malha",
                "id": 6
            },
            {
                "class": "webservicerota.Malha",
                "id": 5
            },
            {
                "class": "webservicerota.Malha",
                "id": 3
            },
            {
                "class": "webservicerota.Malha",
                "id": 4
            },
            {
                "class": "webservicerota.Malha",
                "id": 2
            }
        ],
        "nome": "Mapa1"
    }
}

