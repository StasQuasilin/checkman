package api.summary.reports;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.AnalysesType;
import entity.answers.Answer;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.transport.ActionTime;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import entity.weight.Weight;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.LanguageBase;
import utils.answers.SuccessAnswer;
import utils.files.GeneratedFileUtil;
import utils.hibernate.DateContainers.BETWEEN;
import utils.hibernate.DateContainers.OR;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Created by szpt_user045 on 25.11.2019.
 */
@WebServlet(Branches.API.TRANSPORT_CARRIAGE)
public class TransportationCarriagePrintAPI extends ServletAPI{
    private static final String FROM = Constants.FROM;
    private static final String TO = Constants.TO;
    private static final String ORGANISATION = Constants.ORGANISATION;
    private static final String DRIVER = Constants.DRIVER;
    private static final String TRANSPORTATIONS = Constants.TRANSPORTATIONS;
    private final Logger log = Logger.getLogger(TransportationCarriagePrintAPI.class);
    private static final LocalTime time = LocalTime.of(8, 0);
    private final GeneratedFileUtil generatedFileUtil = new GeneratedFileUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String fileName = req.getParameter(Constants.FILE);
        System.out.println("Load file " + fileName);

        File file = new File(fileName);
        resp.setContentType("APPLICATION/OCTET-STREAM");
        resp.setHeader("Content-Disposition", "attachment; filename=\""
                + file.getName() + "\"");
        final PrintWriter writer = resp.getWriter();

        FileInputStream fis = new FileInputStream(file);
        int i;
        while ((i = fis.read()) != -1){
            writer.write(i);
        }
        fis.close();
        writer.close();
        generatedFileUtil.removeFile(fileName);
        file.delete();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            HashMap<String, Object> params = new HashMap<>();
            Timestamp from = null, to = null;
            int type = Integer.parseInt(String.valueOf(body.get(Constants.TYPE)));
            if (body.containsKey(FROM) && body.containsKey(TO)){
                final LocalDate fromDate = LocalDate.parse(String.valueOf(body.get(FROM)));
                final LocalDate toDate = LocalDate.parse(String.valueOf(body.get(TO)));
                if (type < 2){
                    if (fromDate.equals(toDate)){
                        params.put("date", Date.valueOf(fromDate));
                    } else {
                        params.put("date", new BETWEEN(Date.valueOf(fromDate), Date.valueOf(toDate)));
                    }

                } else if (type == 2){
                    LocalDateTime f = LocalDateTime.of(fromDate, time);
                    LocalDateTime t = LocalDateTime.of(toDate.plusDays(1), time);

                    from = Timestamp.valueOf(f);
                    to = Timestamp.valueOf(t);
                    params.put("timeIn/time", new BETWEEN(from, to));
                }
            }

            if (body.containsKey(ORGANISATION)){
                Organisation organisation = dao.getObjectById(Organisation.class, body.get(ORGANISATION));
                req.setAttribute(ORGANISATION, organisation);
                params.put("deal/organisation", organisation);
            }

            ArrayList<Transportation> transportations = new ArrayList<>();
            if (body.containsKey(PRODUCT)){
                Product product = dao.getObjectById(Product.class, body.get(PRODUCT));
                if (product != null){
                    params.put(PRODUCT, product.getId());
                }
            }
            if (body.containsKey("vehicleContain")){
                Object key = body.get("vehicleContain");
                List<Vehicle> vehicleContain = dao.getVehiclesByName(key);
                log.info("Vehicles, contain " + key.toString() + ": " + vehicleContain.size());
                for (Vehicle v : vehicleContain){
                    HashMap<String, Object> map = new HashMap<>(params);
                    map.put(VEHICLE, v);

                }
            } else {
                JSONArray array = (JSONArray)body.get(DRIVERS);
                OR or = new OR();
                if(array.size() > 0){
                    for (Object o : array){
                        or.add(o);
//                        params.put(DRIVER, o);
//                        transportations.addAll(dao.getObjectsByParams(Transportation.class, params));
                    }
                    params.put(DRIVER, or);
                }
//                else {
//                    transportations.addAll(dao.getObjectsByParams(Transportation.class, params));
//                }
            }

            JSONArray array = (JSONArray) body.get(ORGANISATIONS);
            if (array.size() > 0){
                OR or = new OR();
                for (Object o : array){
                    or.add(o);
                }
                params.put("deal/organisation", or);
            }
            transportations.addAll(dao.getObjectsByParams(Transportation.class, params));
            req.setAttribute(Constants.TYPE, type);
            if (from != null){
                req.setAttribute(FROM, from);
            }
            if (to != null){
                req.setAttribute(TO, to);
            }

            HashMap<Product, LinkedList<Transportation>> hashMap = new HashMap<>();
            for (Transportation t : transportations){
                Product product = t.getProduct();

                if (!hashMap.containsKey(product)) {
                    hashMap.put(product, new LinkedList<>());
                }
                hashMap.get(product).add(t);
            }
            final Comparator<Transportation> comparator = (t1, t2) -> {
                final Date d1 = t1.getDate();
                final Date d2 = t2.getDate();
                if (d1.equals(d2)){
                    Timestamp time1 = null;
                    if (t1.getTimeIn() != null){
                        time1 = t1.getTimeIn().getTime();
                    };
                    Timestamp time2 = null;
                    if (t2.getTimeIn() != null){
                        time2 = t2.getTimeIn().getTime();
                    }
                    if (time1 == null){
                        return 1;
                    } else if (time2 == null){
                        return -1;
                    } else {
                        return time2.compareTo(time1);
                    }
                } else {
                    return d2.compareTo(d1);
                }
            };
            for (LinkedList<Transportation> a : hashMap.values()){
                a.sort(comparator);
            }
            if(body.containsKey(Constants.DOWNLOAD)){
                final String s = saveToExcel(hashMap, type);
                Answer answer = new SuccessAnswer();
                answer.add(Constants.FILE, s);
                write(resp, answer);
            } else {
                req.setAttribute(TRANSPORTATIONS, hashMap);
                if (type == 2) {
                    req.getRequestDispatcher("/pages/print/transportCarriagePrint.jsp").forward(req, resp);
                } else {
                    req.getRequestDispatcher("/pages/print/transportCarriagePrint2.jsp").forward(req, resp);
                }
            }
            hashMap.clear();
        }
    }
    private final LanguageBase languageBase = LanguageBase.getBase();


    private String saveToExcel(HashMap<Product, LinkedList<Transportation>> hashMap, int type) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        final String dateHeader = languageBase.get(Constants.DATE);
        final String timeInHeader = languageBase.get("transportation.time.in");
        final String timeOutHeader = languageBase.get("transportation.time.out");
        final String counterpartyHeader = languageBase.get("deal.organisation");
        final String driverHeader = languageBase.get("transportation.driver");
        final String vehicleHeader = languageBase.get("transportation.automobile");
        final String grossHeader = languageBase.get("weight.gross");
        final String tareHeader = languageBase.get("weight.tare");
        final String netHeader = languageBase.get("weight.net");
        final String creditHeader = languageBase.get("weight.creadit.netto");

        final String sunHumidityHeader = languageBase.get("sun.humidity");
        final String sunSorenessHeader = languageBase.get("sun.soreness");
        final String sunOilinessHeader = languageBase.get("sun.oiliness");
        final String recountHeader = languageBase.get("recount.percentage");

        final String oilColorHeader = languageBase.get("oil.color.value");
        final String sunAcidHeader = languageBase.get("sun.acid.value");
        final String oilPeroxideHeader = languageBase.get("oil.peroxide");
        final String oilPhosphorusHeader = languageBase.get("oil.phosphorus");

        for (Map.Entry<Product, LinkedList<Transportation>> entry : hashMap.entrySet()){
            final Product product = entry.getKey();
            final AnalysesType analysesType = product.getAnalysesType();
            XSSFSheet sheet = workbook.createSheet(product.getName());
            int rowCount = 0;

            //HEADERS
            final Row headerRow = sheet.createRow(rowCount++);
            int col = 0;
            headerRow.createCell(col++).setCellValue(dateHeader);
            if(type == 2){
                headerRow.createCell(col++).setCellValue(timeInHeader);
                headerRow.createCell(col++).setCellValue(timeOutHeader);
            }
            headerRow.createCell(col++).setCellValue(counterpartyHeader);
            headerRow.createCell(col++).setCellValue(driverHeader);
            headerRow.createCell(col++).setCellValue(vehicleHeader);
            if(type == 2){
                headerRow.createCell(col++).setCellValue(grossHeader); //gross
                headerRow.createCell(col++).setCellValue(tareHeader); //tare
                headerRow.createCell(col++).setCellValue(netHeader); //net
                if(analysesType == AnalysesType.sun){
                    headerRow.createCell(col++).setCellValue(creditHeader); //
                }
            }
            //ANALYSES
            if (analysesType == AnalysesType.sun){
                headerRow.createCell(col++).setCellValue(sunHumidityHeader); //
                headerRow.createCell(col++).setCellValue(sunHumidityHeader); //
                headerRow.createCell(col++).setCellValue(sunSorenessHeader); //
                headerRow.createCell(col++).setCellValue(sunOilinessHeader); //
                headerRow.createCell(col).setCellValue(recountHeader); //
            } else if(analysesType == AnalysesType.oil){
                headerRow.createCell(col++).setCellValue(oilColorHeader); //
                headerRow.createCell(col++).setCellValue(sunAcidHeader); //
                headerRow.createCell(col++).setCellValue(oilPeroxideHeader); //
                headerRow.createCell(col++).setCellValue(oilPhosphorusHeader); //
                headerRow.createCell(col).setCellValue(sunHumidityHeader); //
            }
            for (Transportation transportation : entry.getValue()){
                Row r = sheet.createRow(rowCount++);

                col = 0;
                r.createCell(col++).setCellValue(transportation.getDate().toString());
                if(type == 2) {
                    final ActionTime timeIn = transportation.getTimeIn();
                    if (timeIn != null) {
                        r.createCell(col).setCellValue(timeIn.getTime().toString());
                    }
                    col++;

                    final ActionTime timeOut = transportation.getTimeOut();
                    if (timeOut != null) {
                        r.createCell(col).setCellValue(timeOut.getTime().toString());
                    }
                    col++;
                }

                r.createCell(col++).setCellValue(transportation.getDeal().getOrganisation().getValue());
                final Driver driver = transportation.getDriver();
                if(driver != null){
                    r.createCell(col).setCellValue(driver.getPerson().getValue());
                }
                col++;
                final Vehicle vehicle = transportation.getVehicle();
                if(vehicle != null){
                    r.createCell(col++).setCellValue(vehicle.getValue());
                }
                final Weight weight = transportation.getWeight();
                if(type == 2){
                    if(weight != null){
                        r.createCell(col++).setCellValue(weight.getBrutto());
                        r.createCell(col++).setCellValue(weight.getTara());
                        r.createCell(col++).setCellValue(weight.getNetto());
                        if(analysesType == AnalysesType.sun){
                            r.createCell(col).setCellValue(weight.getCorrectedNetto());
                        }
                    }
                }
                if (analysesType == AnalysesType.sun){
                    final SunAnalyses sunAnalyses = transportation.getSunAnalyses();
                    if (sunAnalyses != null){
                        r.createCell(col++).setCellValue(sunAnalyses.getHumidity1());
                        r.createCell(col++).setCellValue(sunAnalyses.getHumidity2());
                        r.createCell(col++).setCellValue(sunAnalyses.getSoreness());
                        r.createCell(col++).setCellValue(sunAnalyses.getOiliness());
                        if (weight != null){
                            r.createCell(col).setCellValue(weight.getCorrection());
                        }
                    }
                } else if (analysesType == AnalysesType.oil){
                    final OilAnalyses oilAnalyses = transportation.getOilAnalyses();
                    if (oilAnalyses != null){
                        r.createCell(col++).setCellValue(oilAnalyses.getColor());
                        r.createCell(col++).setCellValue(oilAnalyses.getAcidValue());
                        r.createCell(col++).setCellValue(oilAnalyses.getPeroxideValue());
                        r.createCell(col++).setCellValue(oilAnalyses.getPhosphorus());
                        r.createCell(col).setCellValue(oilAnalyses.getHumidity());
                    }
                }
            }
        }
        //                builder.append(from).append("-").append(to).append("-");
        final String fileName = UUID.randomUUID().toString().substring(0, 16) + ".xlsx";
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        generatedFileUtil.fixFile(fileName);
        return fileName;
    }
}
