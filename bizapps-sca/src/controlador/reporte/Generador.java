package controlador.reporte;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

/**
 * Servlet implementation class Generador
 */
@WebServlet("/Generador")
public class Generador extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Generador() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CReportes cReportes = new CReportes();
		ServletOutputStream out;
		String par1 = request.getParameter("valor");
		String part2 = request.getParameter("valor2");
		String part3 = request.getParameter("valor3");
		String part4 = request.getParameter("valor4");
		String part5 = request.getParameter("valor5");
		String part6 = request.getParameter("valor6");
		String part7 = request.getParameter("valor7");
		String part8 = request.getParameter("valor8");
		String part9 = request.getParameter("valor9");
		String part10 = request.getParameter("valor10");
		String part11 = request.getParameter("valor11");
		byte[] fichero = null;
		switch (par1) {
		case "1":
			fichero = cReportes.reporte(part2,part3,part4,part5,part6,part7,part8,part9,part10,part11);
			break;
		default:
			break;
		}
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition",
				"inline; filename=Reporte.pdf");
		response.setHeader("Cache-Control", "max-age=30");
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setContentLength(fichero.length);
		out = response.getOutputStream();
		out.write(fichero, 0, fichero.length);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
