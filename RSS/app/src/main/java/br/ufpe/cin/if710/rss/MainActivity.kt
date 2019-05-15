package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : Activity() {

    //ao fazer envio da resolucao, use este link no seu codigo!
    private val RSS_FEED = "http://leopoldomt.com/if1001/g1brasil.xml"

    //OUTROS LINKS PARA TESTAR...
    //http://rss.cnn.com/rss/edition.rss
    //http://pox.globo.com/rss/g1/brasil/
    //http://pox.globo.com/rss/g1/ciencia-e-saude/
    //http://pox.globo.com/rss/g1/tecnologia/

    //use ListView ao invés de TextView - deixe o atributo com o mesmo nome
    private var conteudoRSS: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        conteudoRSS = findViewById(R.id.conteudoRSS)
    }

    override fun onStart() {
        super.onStart()
        try {
            //Esse código dá pau, por fazer operação de rede na thread principal...
            val feedXML = getRssFeed(RSS_FEED)
            conteudoRSS?.text = feedXML
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //Opcional - pesquise outros meios de obter arquivos da internet - bibliotecas, etc.
    @Throws(IOException::class)
    private fun getRssFeed(feed: String): String {
        var input: InputStream? = null
        var rssFeed: String
        try {
            val url = URL(feed)
            val conn = url.openConnection() as HttpURLConnection
            input = conn.inputStream
            val out = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var count: Int

            do {
                count = input!!.read(buffer)
                out.write(buffer, 0, count)
            } while (count != -1)

            val response = out.toByteArray()
            rssFeed = String(response, charset("UTF-8"))
        } finally {
            input?.close()
        }
        return rssFeed
    }
}
