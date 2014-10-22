package api.v1
import grails.rest.RestfulController
import grails.converters.JSON
import grails.transaction.*
import java.io.*
import java.util.*
import webservicerota.*
import webservicerota.DijkstrasShortestPathAlgoritm


@Transactional(readOnly = false)
class MapaController extends RestfulController {
    static namespace = 'v1'

    static responseFormats = ['json']

    MapaController() {

        super(Mapa)
    }

    def index (Integer max){

		params.max = Math.min(max ?: 20, 100)
		respond Mapa.list(max: params.max)
	}

	def save(){

		def request = request.JSON
		def mapa = null
		Mapa.withTransaction { 
			printf request.nome
			mapa = new Mapa(nome : request.nome)
			request.malhas.each { item ->
				def malha = new Malha(origem: item.origem, destino: item.destino, distancia: item.distancia)
				mapa.addToMalhas(malha) 
			}
			mapa.save(flush:true)
		}
		respond mapa
	}
	def show(){

			if(params.pontoInicial != null && params.pontoFinal != null && params.gasolina != null && params.autonomia != null ){

				def response = calcularMenorCaminho(params.id as Long,params.pontoInicial, params.pontoFinal, params.autonomia as Integer, params.gasolina as Float)
				respond([menorCaminho:response.menorCaminho, custo:response.custo, mapa: Mapa.get(params.id)])
			}else{
				respond(Mapa.get(params.id)) 

			} 
	}

	def calcularMenorCaminho(long idMapa, String pontoInicial,String pontoFinal, Integer autonomia,Float gasolina){

		def mapa = Mapa.get(idMapa)

		def graph=[]

		def malhas = mapa?.malhas;
		malhas.each{ malha->
			graph.add(new Edge(node1:malha.origem, node2:malha.destino, distance:malha.distancia))
		}

		def dijkstra = new DijkstrasShortestPathAlgoritm(graph, pontoInicial, pontoFinal)  
		def d = dijkstra.calculateShortestPath()
		log.info d

		def menorCaminho = dijkstra.shortestPath 

		def numeroPontos = menorCaminho.size()

		def primeiro =0;
		def ultimo =0;

		def quilometros= 0;

		def custo = 0;

		while(primeiro < numeroPontos){
				malhas.each{ malha->
					log.info malha.origem + menorCaminho[primeiro]
				if(malha.origem == menorCaminho[primeiro] && malha.destino == menorCaminho[primeiro + 1]){
					quilometros = quilometros + malha.distancia
				}
				primeiro =primeiro +1;
			}
		}

		custo = (quilometros/autonomia) * gasolina;

		def resposta =  new Resposta(custo:custo,menorCaminho:menorCaminho)	
	}

	class Resposta {

	    public Float custo
	    public String menorCaminho
	}

	class Edge {  

	    String node1, node2  
	    int distance  
	}  
}