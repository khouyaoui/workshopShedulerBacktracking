package algorithm;

import com.google.gson.Gson;
import logical.Workshops;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Configs {


    public static Workshops parseToObject(String rutaValida) throws FileNotFoundException {
        Gson gson = new Gson();
        Workshops configuracion;
        return configuracion = gson.fromJson(new FileReader(rutaValida), Workshops.class);
    }
}
