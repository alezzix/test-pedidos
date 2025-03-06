//package utils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import br.com.pedidos.dto.BidRequestDTO;
//
//public class MakeRequestUtil {
//
//	private MakeRequestUtil() {
//		throw new IllegalStateException("Utility class");
//	}
//
//	public static List<BidRequestDTO> makeRequest() {
//
//		List<BidRequestDTO> listRequest = new ArrayList<BidRequestDTO>();
//
//		BidRequestDTO request = new BidRequestDTO();
//		request.setName(RequestsUtils.NAME_MARIA);
//		request.setBid(RequestsUtils.BID_01);
//		listRequest.add(request);
//
//		request = new BidRequestDTO();
//		request.setName(RequestsUtils.NAME_JOAO);
//		request.setBid(RequestsUtils.BID_01);
//		listRequest.add(request);
//
//		request = new BidRequestDTO();
//		request.setName(RequestsUtils.NAME_JOSEFA);
//		request.setBid(RequestsUtils.BID_2);
//		listRequest.add(request);
//
//		return listRequest;
//
//	}
//	
//	public static List<BidRequestDTO> makeWrongRequest() {
//
//		List<BidRequestDTO> listRequest = new ArrayList<BidRequestDTO>();
//
//		BidRequestDTO request = new BidRequestDTO();
//		request.setName(RequestsUtils.NAME_MARIA);
//		request.setBid(RequestsUtils.BID_WRONG);
//		listRequest.add(request);
//
//		return listRequest;
//
//	}
//
//}
