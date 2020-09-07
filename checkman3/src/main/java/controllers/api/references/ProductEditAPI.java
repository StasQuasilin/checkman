package controllers.api.references;

import constants.Apis;
import controllers.api.API;
import entity.analyses.AnalysesType;
import entity.references.Product;
import utils.answer.Answer;
import utils.answer.ErrorAnswer;
import utils.answer.SuccessAnswer;
import utils.db.dao.DaoService;
import utils.db.dao.references.ProductDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(Apis.EDIT_PRODUCT)
public class ProductEditAPI extends API {

    private final ProductDAO productDAO = DaoService.getProductDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if (body != null){
            final Object id = body.get(ID);

            Product product = productDAO.getProductById(id);
            if (product == null){
                product = new Product();
            }
            product.setName(body.getString(NAME));
            if (body.contains(TYPE)){
                AnalysesType analysesType = AnalysesType.valueOf(body.getString(TYPE));
                product.setAnalysesType(analysesType);
            } else {
                product.setAnalysesType(null);
            }

            productDAO.save(product);
            answer = new SuccessAnswer();
            answer.addAttribute(RESULT, product.toJson());

        } else {
            answer = new ErrorAnswer();
        }
        write(resp, answer);
    }
}
