package com.example.pdflibrary

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.Window
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.Toast

object PDF {
    private fun showPdf(context: Context, pdfUrl: String,imageColorForBtnDownloadAndBtnShareLink:Int?=null) {
        var dailog = Dialog(context)
        dailog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dailog!!.setContentView(R.layout.dialog)

        dailog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dailog.show()
        var web_invoice = dailog.findViewById<WebView>(R.id.webView)


        val webViewClient: WebViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                if (view!!.getTitle().equals("")) {
                    view.reload()

                }
                super.onPageFinished(view, url)
            }
        }


        web_invoice.webViewClient = webViewClient
        web_invoice.clearView()
        web_invoice.clearCache(true)
        web_invoice.settings.setSupportZoom(true)
        web_invoice.settings.defaultTextEncodingName = "utf-8"
        web_invoice.settings.loadWithOverviewMode = true
        web_invoice.settings.javaScriptEnabled = true
        web_invoice.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdfUrl)
        var btnDownload = dailog.findViewById<ImageView>(R.id.btnDownload)
        var btnShareLink = dailog.findViewById<ImageView>(R.id.btnShareLink)
        if(imageColorForBtnDownloadAndBtnShareLink!=null){
            btnDownload.setColorFilter(imageColorForBtnDownloadAndBtnShareLink)
            btnShareLink.setColorFilter(imageColorForBtnDownloadAndBtnShareLink)
        }
        btnDownload.setOnClickListener {
            createDownloadListener(pdfUrl,context)
        }
        btnShareLink.setOnClickListener {
            shareUrl(pdfUrl,context)
        }

    }
    private fun shareUrl(pdfUrl: String, context: Context) {
        val shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, pdfUrl)
            this.type = "text/plain"
        }
        context.startActivity(shareIntent)
    }

    private fun createDownloadListener(pdfUrl: String, context: Context) {

        Toast.makeText(context, "starting download   .. ", Toast.LENGTH_LONG).show()
        //downloadListener = DownloadListener { url, userAgent, contentDescription, mimetype, contentLength ->
        val request = android.app.DownloadManager.Request(Uri.parse(pdfUrl))
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        //  val fileName = URLUtil.guessFileName(url, contentDescription, mimetype)
        //  request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        val dManager =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as android.app.DownloadManager
        dManager.enqueue(request)

    }
}