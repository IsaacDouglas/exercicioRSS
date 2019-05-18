package br.ufpe.cin.if710.rss

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_feed.view.*
import kotlinx.android.synthetic.main.itemlista.view.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val task = DownloadTask()
        task.execute(RSS_FEED)
    }

    // Passo 1
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
            var count: Int = 0

            do {
                out.write(buffer, 0, count)
                count = input!!.read(buffer)
            } while (count != -1)

            val response = out.toByteArray()
            rssFeed = String(response, charset("UTF-8"))
        } finally {
            input?.close()
        }
        return rssFeed
    }

    // Passo 2 e 3 no Manifest
    internal inner class DownloadTask : AsyncTask<String, Int, String>() {

        private var feedXML: String? = null

        override fun doInBackground(vararg p0: String?): String? {
            try {
                feedXML = getRssFeed(RSS_FEED)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return feedXML
        }

        override fun onPostExecute(aVoid: String) {
            if (feedXML != null) {
                // Passo 4
                val items = ParserRSS.parse(feedXML!!)

                // Passo 5
                conteudoRSS.apply {
                    layoutManager = LinearLayoutManager(applicationContext)
                    adapter = ItemRSSAdapter(applicationContext, items)
                    addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
                }
            }
        }
    }


    // Passo 6
    internal inner class ItemRSSAdapter (
            var c: Context,
            var items: List<ItemRSS>) :  RecyclerView.Adapter<ItemRSSAdapter.ItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_feed, parent, false)
            return ItemHolder(view)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val item = items[position]
            holder.titulo.text = item.title
            holder.data.text = item.pubDate
            holder.link = item.link
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        internal inner class ItemHolder(val itemLista: View) : RecyclerView.ViewHolder(itemLista) {
            val titulo: TextView = itemLista.item_titulo_feed
            val data: TextView = itemLista.item_data_feed
            var link: String = ""

            init {
                itemLista.setOnClickListener { _ ->
                    // Passo 7
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(link)
                    startActivity(openURL)
                }
            }
        }
    }



}
