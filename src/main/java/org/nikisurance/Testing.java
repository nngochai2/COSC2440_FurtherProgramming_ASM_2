//package org.nikisurance;
//
//import org.nikisurance.entity.InsuranceManager;
//import org.nikisurance.entity.InsuranceSurveyor;
//import org.nikisurance.entity.Provider;
//import org.nikisurance.service.impl.ProviderServiceImpl;
//import org.nikisurance.service.interfaces.ProviderService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Testing {
//    public static void main(String[] args) {
//        System.out.println("Hello World!");
//
//        ProviderService providerService = new ProviderServiceImpl();
//
//        List<Provider> providers = providerService.getAllProviders();
//
//        for (Provider provider : providers) {
//            if (provider instanceof InsuranceSurveyor) {
//                InsuranceSurveyor surveyor = (InsuranceSurveyor) provider;
//
//                List<Provider> insuranceManagers = new ArrayList<>();
//                insuranceManagers.add(manager);
//
//                System.out.println(insuranceManagers);
//            }
//        }
//    }
//}
