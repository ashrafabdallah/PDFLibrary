# PDFLibrary

This library helps us read, download and share PDF files


## Download
Gradle:

repositories {
   maven { url 'https://jitpack.io' }
}



dependencies {
 implementation 'com.github.ashrafabdallah:PDFLibrary:v1.1.0'
	}
## Easy Way To Using Library
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
       
      
       btn.setOnClickListener {
           PDF.showPdf(this,"https://dukqovpycvvvc.pdf")
       }
    }
    }
