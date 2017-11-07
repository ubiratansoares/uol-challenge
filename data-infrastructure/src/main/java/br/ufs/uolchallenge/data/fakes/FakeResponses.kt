package br.ufs.uolchallenge.data.fakes

import br.ufs.uolchallenge.data.models.NewsFeedPayload
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

/**
 * Created by bira on 11/7/17.
 */
object FakeResponses {

    private val deserializer = Gson()

    fun newsFeed(): Observable<NewsFeedPayload> {
        return Observable.just<NewsFeedPayload>(fakeNewsFeed())
    }

    fun internalServerError(): Observable<NewsFeedPayload> {
        val response = Response.error<NewsFeedPayload>(503, bodyFor(internalServerErrorMessage()))
        val exp = HttpException(response)
        val error = Observable.error<NewsFeedPayload>(exp)
        return error.delay(1, TimeUnit.SECONDS)
    }

    fun notFound(): Observable<NewsFeedPayload> {
        val response = Response.error<NewsFeedPayload>(404, bodyFor(notFoundMessage()))
        val exp = HttpException(response)
        return Observable.error(exp)
    }

    fun connectionIssue(): Observable<NewsFeedPayload> {
        return Observable.error(IOException("Canceled"))
    }

    fun requestTimeout(): Observable<NewsFeedPayload> {
        return Observable.error(SocketTimeoutException("Read timeout"))
    }


    fun clientError(): Observable<NewsFeedPayload> {
        val response = Response.error<NewsFeedPayload>(400, bodyFor(badRequest()))
        val exp = HttpException(response)
        return Observable.error(exp)
    }

    private fun badRequest(): String {
        return "  \"Error\": \"Bad request.\",\n"
    }

    private fun bodyFor(error: String): ResponseBody {
        return ResponseBody.create(MediaType.parse("application/json"), error)
    }

    private fun internalServerErrorMessage(): String {
        return "  \"Error\": \"Out of capacity.\",\n"
    }

    private fun notFoundMessage(): String {
        return "  \"Error\": \"Not Found.\",\n"
    }

    private fun fakeNewsFeed(): NewsFeedPayload {
        val json = "{\n" +
                "  \"feed\": [\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Santos fecha com atacante de Burkina Faso e que se destacou no Londrina\",\n" +
                "      \"thumb\": \"https://imguol.com/c/esporte/2b/2017/09/05/yaya-banhoro-burkina-faso-1504648624490_142x100.jpg\",\n" +
                "      \"updated\": 20171101175506,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/2017/11/01/santos-fecha-com-atacante-de-brukina-faso-e-que-se-destacou-no-londrina.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/2017/11/01/santos-fecha-com-atacante-de-brukina-faso-e-que-se-destacou-no-londrina.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Sem treinar desde segunda-feira, Copete vira dúvida para o Santos\",\n" +
                "      \"updated\": 20171101174753,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/sem-treinar-desde-segunda-feira-copete-vira-duvida-para-o-santos.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/sem-treinar-desde-segunda-feira-copete-vira-duvida-para-o-santos.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Ex-técnico do Lanús morre um dia após classificação na Libertadores\",\n" +
                "      \"updated\": 20171101174635,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/ex-tecnico-do-lanus-morre-um-dia-apos-classificacao-na-libertadores.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/ex-tecnico-do-lanus-morre-um-dia-apos-classificacao-na-libertadores.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Mancuello comemora a classificação do Lanús, mas irrita torcedores\",\n" +
                "      \"updated\": 20171101174139,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/mancuello-comemora-a-classificacao-do-lanus-mas-irrita-torcedores.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/mancuello-comemora-a-classificacao-do-lanus-mas-irrita-torcedores.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Filho de Fernandão segue passos do pai e assina contrato com Inter\",\n" +
                "      \"thumb\": \"https://imguol.com/c/esporte/43/2017/11/01/enzo-filho-de-fernandao-assinou-contrato-com-o-internacional-aos-14-anos-1509564709285_142x100.jpg\",\n" +
                "      \"updated\": 20171101173900,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/2017/11/01/filho-de-fernandao-segue-passos-do-pai-e-assina-contrato-com-inter.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/2017/11/01/filho-de-fernandao-segue-passos-do-pai-e-assina-contrato-com-inter.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Antes de Dérbi, Edilson dá autógrafos em Tour da Arena Corinthians\",\n" +
                "      \"updated\": 20171101172633,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/antes-de-derbi-edilson-da-autografos-em-tour-da-arena-corinthians.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/antes-de-derbi-edilson-da-autografos-em-tour-da-arena-corinthians.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Jornal: Ainda lesionado, Iniesta deve desfalcar o Barcelona no Espanhol\",\n" +
                "      \"thumb\": \"https://imguol.com/c/esporte/7b/2017/09/28/andres-iniesta-durante-partida-do-barcelona-contra-o-sporting-1506596981349_142x100.jpg\",\n" +
                "      \"updated\": 20171101170603,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/2017/11/01/jornal-ainda-lesionado-iniesta-deve-desfalcar-o-barcelona-no-espanhol.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/2017/11/01/jornal-ainda-lesionado-iniesta-deve-desfalcar-o-barcelona-no-espanhol.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Edenílson celebra volta de Dourado ao Inter: 'Facilita muito jogar com ele'\",\n" +
                "      \"thumb\": \"https://imguol.com/c/esporte/26/2017/11/01/edenilson-comemora-gol-pelo-internacional-na-segunda-divisao-brasileira-1509562701362_142x100.jpg\",\n" +
                "      \"updated\": 20171101170356,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/campeonatos/brasileiro/serie-b/ultimas-noticias/2017/11/01/edenilson-celebra-volta-de-dourado-ao-inter-facilita-muito-jogar-com-ele.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/campeonatos/brasileiro/serie-b/ultimas-noticias/2017/11/01/edenilson-celebra-volta-de-dourado-ao-inter-facilita-muito-jogar-com-ele.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Besiktas arranca empate, encaminha vaga e complica a vida do Monaco\",\n" +
                "      \"thumb\": \"https://imguol.com/c/esporte/13/2017/11/01/adriano-do-besiktas-e-fabinho-do-monaco-se-desentendem-1509563870159_142x100.jpg\",\n" +
                "      \"updated\": 20171101165816,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/besiktas-arranca-empate-encaminha-vaga-e-complica-a-vida-do-monaco.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/besiktas-arranca-empate-encaminha-vaga-e-complica-a-vida-do-monaco.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Alberto Valentim faz mistério e 'esconde' Palmeiras a quatro dias do dérbi\",\n" +
                "      \"thumb\": \"https://imguol.com/c/esporte/6c/2017/10/22/alberto-valentim-tecnico-do-palmeiras-orienta-o-time-no-jogo-contra-o-gremio-1508710489896_142x100.jpg\",\n" +
                "      \"updated\": 20171101165532,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/campeonatos/brasileiro/serie-a/ultimas-noticias/2017/11/01/alberto-valentim-faz-misterio-e-esconde-palmeiras-a-tres-dias-do-derbi.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/campeonatos/brasileiro/serie-a/ultimas-noticias/2017/11/01/alberto-valentim-faz-misterio-e-esconde-palmeiras-a-tres-dias-do-derbi.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Vanderlei cobra mudança de postura do Santos: 'Não podemos só falar'\",\n" +
                "      \"thumb\": \"https://imguol.com/c/esporte/d1/2017/10/16/vanderlei-acena-para-colegas-do-santos-na-partida-contra-o-vitoria-1508197922157_142x100.jpg\",\n" +
                "      \"updated\": 20171101165411,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/vanderlei-cobra-mudanca-de-postura-do-santos-nao-podemos-so-falar.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/vanderlei-cobra-mudanca-de-postura-do-santos-nao-podemos-so-falar.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Gabriel Jesus ficará no banco do Manchester City em jogo contra o Napoli\",\n" +
                "      \"thumb\": \"https://imguol.com/c/esporte/91/2017/01/20/gabriel-jesus-chega-com-moral-ao-manchester-city-1484907400358_142x100.jpg\",\n" +
                "      \"updated\": 20171101164641,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/campeonatos/liga-dos-campeoes/ultimas-noticias/2017/11/01/gabriel-jesus-ficara-no-banco-do-manchester-city-em-jogo-contra-o-napoli.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/campeonatos/liga-dos-campeoes/ultimas-noticias/2017/11/01/gabriel-jesus-ficara-no-banco-do-manchester-city-em-jogo-contra-o-napoli.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Jogadores 'vão dar a vida' pela efetivação de Valentim, assegura Dudu\",\n" +
                "      \"thumb\": \"https://imguol.com/c/parceiros/0f/2017/10/30/alberto-valentim-comanda-o-palmeiras-hoje-a-noite-1509362298393_v2_142x100.jpg\",\n" +
                "      \"updated\": 20171101163843,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/2017/11/01/jogadores-vao-dar-a-vida-pela-efetivacao-de-valentim-assegura-dudu.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/2017/11/01/jogadores-vao-dar-a-vida-pela-efetivacao-de-valentim-assegura-dudu.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Brasileiro celebra primeiro gol como profissional sobre time de David Villa\",\n" +
                "      \"updated\": 20171101163817,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/brasileiro-celebra-primeiro-gol-como-profissional-sobre-time-de-david-villa.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/brasileiro-celebra-primeiro-gol-como-profissional-sobre-time-de-david-villa.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Sergi Roberto e André Gomes devem desfalcar o Barcelona por um mês\",\n" +
                "      \"updated\": 20171101163814,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/sergi-roberto-e-andre-gomes-devem-desfalcar-o-barcelona-por-um-mes.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/sergi-roberto-e-andre-gomes-devem-desfalcar-o-barcelona-por-um-mes.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Dudu diz que todo o elenco do Palmeiras quer efetivação de Alberto\",\n" +
                "      \"updated\": 20171101163357,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/dudu-diz-que-todo-o-elenco-do-palmeiras-quer-efetivacao-de-alberto.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/dudu-diz-que-todo-o-elenco-do-palmeiras-quer-efetivacao-de-alberto.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Cruzeiro divulga detalhes do terceiro uniforme da temporada\",\n" +
                "      \"updated\": 20171101162837,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/cruzeiro-divulga-detalhes-do-terceiro-uniforme-da-temporada.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/cruzeiro-divulga-detalhes-do-terceiro-uniforme-da-temporada.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"news\",\n" +
                "      \"title\": \"Militão sofre estiramento na coxa esquerda e desfalca o São Paulo\",\n" +
                "      \"updated\": 20171101162814,\n" +
                "      \"share-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/militao-sofre-estiramento-na-coxa-esquerda-e-desfalca-o-sao-paulo.htm\",\n" +
                "      \"webview-url\": \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2017/11/01/militao-sofre-estiramento-na-coxa-esquerda-e-desfalca-o-sao-paulo.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
                "    }\n" +
                "]"

        return deserializer.fromJson(json, NewsFeedPayload::class.java)
    }

}