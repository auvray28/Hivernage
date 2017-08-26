package com.meeple.cloud.hivernage.db;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.meeple.cloud.hivernage.db.annotation.Column;
import com.meeple.cloud.hivernage.db.annotation.GsonTransient;
import com.meeple.cloud.hivernage.db.annotation.Id;
import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.EmplacementCamping;
import com.meeple.cloud.hivernage.model.EmplacementHangar;
import com.meeple.cloud.hivernage.model.Entity;
import com.meeple.cloud.hivernage.model.Facture;
import com.meeple.cloud.hivernage.model.Gabarit;
import com.meeple.cloud.hivernage.model.Hangar;
import com.meeple.cloud.hivernage.model.Hivernage;
import com.meeple.cloud.hivernage.model.Paiement;
import com.meeple.cloud.hivernage.model.Tranche;
import com.meeple.cloud.hivernage.model.tools.SharedPreferenceHelper;
import com.meeple.cloud.hivernage.service.Services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

@SuppressLint("DefaultLocale")
@SuppressWarnings("rawtypes")
public class DbHelper /* extends SQLiteOpenHelper */{

	public final static Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();
	public final static ArrayList<Class<? extends Entity>> entityList = new ArrayList<Class<? extends Entity>>();
	public static String TAG = "POUET";
	static {
		map.put(boolean.class, Boolean.class);
		map.put(byte.class, Byte.class);
		map.put(short.class, Short.class);
		map.put(char.class, Character.class);
		map.put(int.class, Integer.class);
		map.put(long.class, Long.class);
		map.put(float.class, Float.class);
		map.put(double.class, Double.class);

		entityList.add(Camping.class);
		entityList.add(Caravane.class);
		entityList.add(Client.class);
		entityList.add(EmplacementCamping.class);
		entityList.add(EmplacementHangar.class);
		entityList.add(Facture.class);
		entityList.add(Gabarit.class);
		entityList.add(Hangar.class);
		entityList.add(Hivernage.class);
		entityList.add(Paiement.class);
		entityList.add(Tranche.class);

	}


	public static DbHelper instance;

	private SharedPreferenceHelper helper;
	ExclusionStrategy excludeStrings = new GsonExclusionStrategy();
	Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy[] { this.excludeStrings }).create();

	// Permet d'évité tout les saveModels lorsque l'on importe une grosse quantité d'information
	//
	public boolean isImportingModel = false;

	public DbHelper(Context context, SharedPreferenceHelper sharedPrefHelder) {
		//super(context, "hivernage.db", null, 5);
		this.helper = sharedPrefHelder;
	}


/*
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "call onCreate");

		for(Class<? extends Entity> c : entityList) {
			try {
				Entity<?> e = (Entity) c.newInstance();
				String createTable = e.createTable();
				Log.i(TAG, createTable);
				db.execSQL(createTable);
			} catch (Exception e) {
				Log.e(TAG,"create table fail : "+entityToTableName(c.getSimpleName()),e);
			} 
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG,"delete db first");
		for(Class<? extends Entity> c : entityList) {
			db.execSQL("drop table if exists " + entityToTableName(c.getSimpleName()));
		}
		onCreate(db);
	}

*/

	public static String fieldToSetter(String f) {
		return "set" + f.substring(0, 1).toUpperCase() + f.substring(1);
	}

	public static String fieldToGetter(String f) {
		return "get" + f.substring(0, 1).toUpperCase() + f.substring(1);
	}

	public static String fieldToColName(String f) {
		String res = "";
		for (char c : f.toCharArray()) {
			if (Character.isLowerCase(c)) {
				res += Character.toUpperCase(c);
			} else if (Character.isUpperCase(c)) {
				res += "_" + c;
			}
		}
		return res;
	}

	public static String colNameToField(String s) {
		String res = "";
		boolean needUpperCase = false;
		for (char c : s.toCharArray()) {
			if (c != '_') {
				if (!needUpperCase) {
					res += Character.toLowerCase(c);
				} else {
					res += c;
					needUpperCase = false;
				}
			} else {
				needUpperCase = true;
			}
		}
		return res;
	}

	public static String entityToTableName(String entityName) {
		return fieldToColName(entityName.substring(0,1).toLowerCase()+entityName.substring(1));
	}


	public void executeStatement(String sql) {
		// NICO
		//getWritableDatabase().execSQL(sql);
	}

	public static String getColName(Field f) {
		String colName = null;
		if(f.getAnnotation(Column.class) != null) {
			colName = f.getAnnotation(Column.class).colName();
			if(colName.isEmpty()) {
				colName = fieldToColName(f.getName());
			}
		}

		return colName;
	}


	public static String getType(Field f) {
		Class<? extends Object> type = f.getType();
		String typeStr = null;
		if (type.isPrimitive()) {
			typeStr = map.get(type).getSimpleName();
		} else if (type.getSuperclass().getSimpleName().equals("Entity")) {
			typeStr = "Integer";
		}
		else if (type.getSimpleName().equals("ArrayList")) {

		}else if(type.getSimpleName().equals("String")) {
			typeStr = "TEXT";
		}

		else {
			typeStr = type.getSimpleName();
		}
		if(typeStr != null && f.getAnnotation(Id.class) != null) {
			typeStr+= " PRIMARY KEY";
		}
		return typeStr;
	}

	public static DbHelper getInstance() {
		return instance;
	}

	public static void sql(String sql) {
		instance.executeStatement(sql);
	}


	// Chargement du model sauvegardé dans les prefs via JSon
	//
	public ArrayList<Camping> parseCampingsFromJson(String paramString)
	{
		Type localType = new TypeToken<List<Camping>>(){}.getType();
		return (ArrayList)this.gson.fromJson(paramString, localType);
	}

	public ArrayList<Client> parseClientListFromJson(String paramString)
	{
		Type localType = new TypeToken<List<Client>>(){}.getType();
		return (ArrayList)this.gson.fromJson(paramString, localType);
	}

	public Client parseFromJson(String paramString)
	{
		Client loadClient = (Client)this.gson.fromJson(paramString, Client.class);
		loadClient.getCaravane().setClient(loadClient);
		return loadClient;
	}

	public ArrayList<Gabarit> parseGabarisFromJson(String paramString)
	{
		Type localType = new TypeToken<List<Gabarit>>(){}.getType();
		return (ArrayList)this.gson.fromJson(paramString, localType);
	}

	public ArrayList<Hangar> parseHangarFromJson(String paramString)
	{
		Type localType = new TypeToken<List<Hangar>>(){}.getType();
		return (ArrayList)this.gson.fromJson(paramString, localType);
	}

	public HashMap<String, Integer> parseIdsFromJson(String paramString)
	{
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		Type localType = new TypeToken<Map<String, String>>(){}.getType();
		
		Map<String,String> map = gson.fromJson(paramString, localType);

		if (map != null) {
			for(String key : map.keySet()) {
				
				int value = Integer.parseInt(map.get(key));
				
				hmap.put(key, value);
			}
		}
		
		return hmap;
	}


	public String toJson(Client paramClient) {
		return this.gson.toJson(paramClient);
	}

	public String toJson(ArrayList<Client> listeClients) {
		ArrayList<Client> localListeClient = new ArrayList<Client>();

		for(Client client : listeClients) {
			localListeClient.add(client.clientForJson());
		}

		return this.gson.toJson(localListeClient);
	}


	private static class GsonExclusionStrategy 	implements ExclusionStrategy
	{
		@Override
		public boolean shouldSkipClass(Class<?> paramClass) {
			return paramClass.getAnnotation(GsonTransient.class) != null;
		}

		@Override
		public boolean shouldSkipField(FieldAttributes fieldAttr) {
			return fieldAttr.getAnnotation(GsonTransient.class) != null;
		}
	}


	// Sauvegarde du model dans les prefs via JSon
	public void saveModel() {
		if (!isImportingModel) {
			this.helper.saveClients(this.gson.toJson(DBMock.DB.getClients()));
			Log.i("db", "nb clients saved: " + DBMock.DB.getClients().size());
			
			this.helper.saveCampings(this.gson.toJson(DBMock.DB.getCampings()));
			Log.i("db", "nb campings saved: " + DBMock.DB.getCampings().size());
			
			this.helper.saveHangars(this.gson.toJson(DBMock.DB.getHangars()));
			Log.i("db", "nb hangars saved: " + DBMock.DB.getHangars().size());
			
			this.helper.saveGabaris(this.gson.toJson(DBMock.DB.getGabarits()));
			Log.i("db", "nb gabarits saved: " + DBMock.DB.getGabarits().size());
			
			this.helper.saveIds(this.gson.toJson(DBMock.DB.getLastIdsForJSon()));
			Log.i("db", "ids saved: " + this.gson.toJson(DBMock.DB.getLastIds()));
			Log.i("db", this.helper.getClients());
			Log.i("db", this.helper.getCampings());
			Log.i("db", this.helper.getHangars());
			Log.i("db", this.helper.getGabarits());
		}
	}

	
	/* Load JSon via GSon */
	public void load() {
        if (this.helper.isInitialized()) {
            Iterator it;
            Log.i("db", "load init");
            String clientsSaved = this.helper.getClients();
            if (!"".equals(clientsSaved)) {
                it = parseClientListFromJson(clientsSaved).iterator();
                while (it.hasNext()) {
                    Client c = (Client) it.next();
                    DBMock.DB.getClients().add(c);
                    DBMock.DB.getCaravanes().add(c.getCaravane());
                    c.getCaravane().setClient(c);
                    Iterator it2 = c.getCaravane().getEmplacementCamping().iterator();
                    while (it2.hasNext()) {
                        EmplacementCamping ec = (EmplacementCamping) it2.next();
                        Camping campingSaved = Services.campingService.findCampingById(ec.getCamping().getCampingId());
                        if (campingSaved != null) {
                            ec.setCamping(campingSaved);
                            campingSaved.getEmplacements().add(ec);
                        } else {
                            ec.setCaravane(c.getCaravane());
                            ec.getCamping().getEmplacements().add(ec);
                            DBMock.DB.getCampings().add(ec.getCamping());
                        }
                    }
                    if (c.getCaravane().getEmplacementHangar() != null) {
                        Hangar saved = Services.hangarService.findHangarById(c.getCaravane().getEmplacementHangar().getHangar().getHangarId());
                        if (saved != null) {
                            c.getCaravane().getEmplacementHangar().setHangar(saved);
                            saved.addCaravane(c.getCaravane());
                        } else {
                            c.getCaravane().getEmplacementHangar().getHangar().addCaravane(c.getCaravane());
                            DBMock.DB.getHangars().add(c.getCaravane().getEmplacementHangar().getHangar());
                        }
                    }
                }
            }
            Log.i("db", "hangard loaded by user:" + DBMock.DB.getHangars());
            Log.i("db", "nb clients loaded: " + DBMock.DB.getClients().size());
            String CampingSaved = this.helper.getCampings();
            if (!"".equals(CampingSaved)) {
                it = parseCampingsFromJson(CampingSaved).iterator();
                while (it.hasNext()) {
                	Boolean addNewCamping = true;
                	Camping h = (Camping) it.next();
                    
                    for (Camping h2 : DBMock.DB.getCampings()) {
                    	if (h2.getNom().equals(h.getNom())) addNewCamping = false;
                    }
                    	
                    if (addNewCamping) {
                        DBMock.DB.getCampings().add(h);
                    }
                }
            }
            Log.i("db", "nb campings loaded: " + DBMock.DB.getCampings().size());
            String gabaritsSaved = this.helper.getGabarits();
            if (!"".equals(gabaritsSaved)) {
                it = parseGabarisFromJson(gabaritsSaved).iterator();
                while (it.hasNext()) {
                	Boolean addNewGabarit = true;
                	Gabarit g = (Gabarit) it.next();
                    
                    for (Gabarit g2 : DBMock.DB.getGabarits()) {
                    	if (g2.getNom().equals(g.getNom())) addNewGabarit = false;
                    }
                    	
                    if (addNewGabarit) {
                        DBMock.DB.getGabarits().add(g);
                    }
                }
            }
            Log.i("db", "nb gabarits loaded: " + DBMock.DB.getGabarits().size());
            String HangarSaved = this.helper.getHangars();
            if (!"".equals(HangarSaved)) {
                it = parseHangarFromJson(HangarSaved).iterator();
                while (it.hasNext()) {
                	Boolean addNewHangar = true;
                    Hangar h = (Hangar) it.next();
                    
                    for (Hangar h2 : DBMock.DB.getHangars()) {
                    	if (h2.getNom().equals(h.getNom())) addNewHangar = false;
                    }
                    	
                    if (addNewHangar) {
                        DBMock.DB.getHangars().add(h);
                    }
                }
            }
            Log.i("db", "nb hangars loaded: " + DBMock.DB.getHangars().size());
            String idsSaved = this.helper.getIds();
            if (!"".equals(idsSaved)) {
                DBMock.DB.setLastIds(parseIdsFromJson(idsSaved));
            }
            Log.i("db", "ids loaded: " + DBMock.DB.getLastIds());
            return;
        }
        Log.i("db", "first init");
        DBMock.DB.createDefaultObject();
        saveModel();
        this.helper.setInitialized();
    }

}
