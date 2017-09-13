package io.tutorial.turntotech.infoOrganizerSample;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Jeremy on 7/27/2017.
 */

public class DAO {

    private static ArrayList<Company> companyList;
    private static int companyNo, productNo;
    private static boolean edit;
    private static String stockURL;
    private ICallBack callBack;

    static DAO instance;
    static DatabaseHelper helper;
    public static DAO getInstance(){
        if (instance == null){
            instance = new DAO();
            instance.getFromDB();
            if(companyList.size() == 0){
                instance.createDummy();
                instance.getFromDB();
            }

        }
        return instance;
    }

    public static DatabaseHelper getHelper(Context context){
        if (helper == null){
            helper = new DatabaseHelper(context);
        }
        return helper;
    }

    public void setCallBack(ICallBack callBack){
        this.callBack = callBack;
    }

    public static ArrayList<Company> getcompanyList(){
        return companyList;
    }

    public static int getCompanyNo(){
        return companyNo;
    }

    public static void setCompanyNo(int i){
        companyNo = i;
    }

    public static int getProductNo(){
        return productNo;
    }

    public static void setProductNo(int i){
        productNo = i;
    }

    public static String getStockURL(){
        stockURL = "https://download.finance.yahoo.com/d/quotes.csv?s=";
        for (int i = 0; i < DAO.getcompanyList().size(); i++){
            if (i != 0){
                stockURL = stockURL + "+";
            }
            stockURL = stockURL + DAO.getcompanyList().get(i).getStock_ticker();
        }
        stockURL = stockURL + "&f=nl1";
        return stockURL;
    }

    public void sendVolleyRequest(Context context){
        StringRequest stringRequest = new StringRequest(getStockURL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        updatePrices(response);
                        callBack.updateStockPrice();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void updatePrices(String string){
        String temp[] = string.split("\n");
        ArrayList<String> temp2 = new ArrayList<String>();
        for (String s : temp){
            String temp3[] = s.split(",");
            for (String c : temp3){
                temp2.add(c);
            }
        }
        ArrayList<String> last = new ArrayList<String>();
        for (int i = 0; i < temp2.size(); i++){
            if (i % 2 == 1){
                last.add(temp2.get(i));
            }
        }
        for (int n = 0; n < DAO.getcompanyList().size(); n++){
            DAO.getcompanyList().get(n).setStock_price(last.get(n));
        }
    }

    private DAO(){
        companyList = new ArrayList<Company>();
    }

    public void createDummy(){
        Company Apple = new Company("Apple", "https://seeklogo.com/images/A/Apple-logo-4DC2B05F7D-seeklogo.com.png", "AAPL");
        Product iPhone = new Product("iPhone",
                "https://staticshop.o2.co.uk/product/images/apple_iphone_7_plus_128gb_red_sku_header.png?cb=2077cd5d85e2dd9b6905a70ad30e33ba",
                "https://www.apple.com/iphone/",
                Apple);
        Product iPad = new Product("iPad",
                "https://pisces.bbystatic.com/BestBuy_US/store/rtb/experience/apple/images/iPad-img.png",
                "https://www.apple.com/ipad/",
                Apple);
        Product Mac = new Product("Macintosh",
                "https://www.imobie.com/support/img/retina-macbook-12-inch.png",
                "https://www.apple.com/mac/",
                Apple);

        Company Samsung = new Company("Samsung", "http://assets.stickpng.com/thumbs/580b57fcd9996e24bc43c533.png", "005930.KS");
        Product GalaxyS = new Product("Galaxy S",
                "http://store.virginmedia.com/content/dam/eSales/mobile/responsive/Samsung/Zoom/galaxy_s8_grey_comp.png",
                "http://www.samsung.com/us/explore/galaxy-s8/",
                Samsung);
        Product GalaxyNote = new Product("Galaxy Note",
                "http://www.adweek.com/wp-content/uploads/files/2016_Sep/note7_samsung-fire-hed-2016.png",
                "https://www.phonearena.com/phones/Samsung-Galaxy-Note-Fan-Edition_id10591",
                Samsung);
        Product GalaxyBook = new Product("Galaxy Book",
                "http://images.samsung.com/is/image/samsung/p5/uk/pcd/mobile/PCD_Mobile_Tablet_03_Featurebenefit_PC_img_20160831.png?$ORIGIN_PNG$",
                "http://www.samsung.com/us/explore/galaxy-book/",
                Samsung);

        Company Motorola = new Company("Motorola", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Motorola_Mobility_Logo_2015.svg/1280px-Motorola_Mobility_Logo_2015.svg.png", "0992.HK");
        Product MotoZ = new Product("Moto Z",
                "http://www.zailet.com/posts/post60/3.jpg",
                "https://www.motorola.com/us/products/moto-z-force-edition-gen-2",
                Motorola);
        Product MotoG = new Product("Moto G",
                "http://www3.lenovo.com/medias/lenovo-moto-g5-plus-hero.png?context=bWFzdGVyfGltYWdlc3w5MTE5M3xpbWFnZS9wbmd8aW1hZ2VzL2gxMy9oZDgvOTQwMTc4NjY2Mjk0Mi5wbmd8Y2VhMDgxNDg3ZjQwZDJkNGM3OWFmMzJjNzcxNjljY2JhNDA4ZjI5YzMyMDY1NTgzNjc4MWVhOTVmOTM1Y2M5ZA",
                "https://www.motorola.com/us/products/moto-g-plus",
                Motorola);
        Product MotoE = new Product("Moto E",
                "https://www.motorola.com/sites/default/files/library/storage/products/smartphones/moto-e4-plus-NA-1000.png",
                "https://www.motorola.com/us/products/moto-e-plus-gen-4",
                Motorola);

        Company Microsoft = new Company("Microsoft", "http://pngimg.com/uploads/microsoft/microsoft_PNG16.png", "MSFT");
        Product Xbox = new Product("Xbox One",
                "http://images.bbycastatic.ca/sf/projects/projectscorpio/assets/xbox-one-x-console.png",
                "http://www.xbox.com/en-us/xbox-one-x",
                Microsoft);
        Product SurfacePro = new Product("Surface Pro",
                "https://surfacetip.com/wp-content/uploads/2017/01/surface-pro-4-png-1-300x169.png",
                "https://www.microsoft.com/en-us/surface/devices/surface-pro/overview",
                Microsoft);
        Product SurfaceBook = new Product("Surface Laptop",
                "https://d243u7pon29hni.cloudfront.net/images/products/portatil-microsoft-surface-london-platino-1364584-10_ad_l.png",
                "https://www.microsoft.com/en-us/surface/devices/surface-laptop/overview",
                Microsoft);

        Company LG = new Company("LG", "https://seeklogo.com/images/L/LG-logo-1409344847-seeklogo.com.png", "LGLD.IL");
        Product LGG = new Product("LG G",
                "https://www.androidcentral.com/sites/androidcentral.com/files/styles/large/public/topic_images/2017/lg-g6-02.png?itok=6GoJ4X3S",
                "http://www.lg.com/us/mobile-phones/g6",
                LG);
        Product Gram = new Product("LG Gram",
                "http://www.lg.com/us/images/stepupChart/15z.png",
                "http://www.lg.com/us/laptops",
                LG);
        Product TV = new Product("LG OLED TV",
                "https://www.fouanistore.com/images/thumbnails/465/465/detailed/3/_55%E2%80%9D_LG_OLED_TV_55EG910_(2).gif",
                "http://www.lg.com/us/oled-tvs",
                LG);

        Company HTC = new Company("HTC", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Htc_new_logo.svg/500px-Htc_new_logo.svg.png", "2498.TW");
        Product U11 = new Product("HTC U11",
                "https://www.htc.com/managed-assets/shared/desktop/smartphones/htc-u11/explorer/en_US/htc-u11-black-global-phone-listing.png",
                "http://www.htc.com/us/smartphones/htc-u11/",
                HTC);
        Product UUltra = new Product("HTC U Ultra",
                "http://www.htc.com/managed-assets/shared/desktop/smartphones/htc-u-ultra/explorer/htc-u-ultra-blue-global-phone-listing.png",
                "http://www.htc.com/us/smartphones/htc-u-ultra/",
                HTC);
        Product Vive = new Product("HTC Vive",
                "https://www.vive.com/media/filer_public/b8/96/b896b746-0118-4105-93b6-4270db79e1a1/product-vive-family-shot.png",
                "https://www.vive.com/us/",
                HTC);

        try{
            helper.getmCompanyDao().create(Apple);
            helper.getmCompanyDao().create(Samsung);
            helper.getmCompanyDao().create(Microsoft);
            helper.getmCompanyDao().create(Motorola);
            helper.getmCompanyDao().create(LG);
            helper.getmCompanyDao().create(HTC);

            helper.getmProductDao().create(iPhone);
            helper.getmProductDao().create(iPad);
            helper.getmProductDao().create(Mac);
            helper.getmProductDao().create(GalaxyS);
            helper.getmProductDao().create(GalaxyNote);
            helper.getmProductDao().create(GalaxyBook);
            helper.getmProductDao().create(Xbox);
            helper.getmProductDao().create(SurfaceBook);
            helper.getmProductDao().create(SurfacePro);
            helper.getmProductDao().create(MotoZ);
            helper.getmProductDao().create(MotoG);
            helper.getmProductDao().create(MotoE);
            helper.getmProductDao().create(LGG);
            helper.getmProductDao().create(Gram);
            helper.getmProductDao().create(TV);
            helper.getmProductDao().create(U11);
            helper.getmProductDao().create(UUltra);
            helper.getmProductDao().create(Vive);
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public void AddCompany(String Name, String LogoURL, String StockTicker){
        Company company = new Company(Name, LogoURL, StockTicker);
        DAO.getcompanyList().add(company);
    }

    public void AddProduct(String Name, String LogoURL, String ProductURL){
        Product product = new Product(Name, LogoURL, ProductURL, DAO.getcompanyList().get(DAO.getCompanyNo()));
        try {
            helper.getmProductDao().create(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setEdit(boolean bool) {
        edit = bool;
    }

    public static boolean getEdit() {
        return edit;
    }

    public void getFromDB(){
        try {
            companyList.clear();
            companyList = (ArrayList<Company>) helper.getmCompanyDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
