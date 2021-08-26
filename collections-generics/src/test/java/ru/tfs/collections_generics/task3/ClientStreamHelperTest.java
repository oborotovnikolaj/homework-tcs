package ru.tfs.collections_generics.task3;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static ru.tfs.collections_generics.task3.ClientStreamHelper.*;

public class ClientStreamHelperTest {

    @Test
    public void getAgeSumByNameTest() {

        ClientTask3 chuckNorris = new ClientTask3(Integer.MAX_VALUE, "Chuck Norris", Integer.MAX_VALUE, null);

        List<PhoneTask3> phoneTask3s = Arrays.asList(
                new PhoneTask3(TelephoneTypeTask3.HOME_PHONE, "111111"),
                new PhoneTask3(TelephoneTypeTask3.HOME_PHONE, "222222"),
                new PhoneTask3(TelephoneTypeTask3.MOBILE_PHONE, "11111111111"),
                new PhoneTask3(TelephoneTypeTask3.MOBILE_PHONE, "22222222222")
        );

        ClientTask3 sasha_grey = new ClientTask3(1001, "Sasha Grey", 18, phoneTask3s);
        ClientTask3 sasha_grey2 = new ClientTask3(1002, "Sasha Grey", 35, phoneTask3s);

        ClientTask3 elsa_jean = new ClientTask3(1003, "Elsa Jean", 18, phoneTask3s);
        ClientTask3 elsa_jean2 = new ClientTask3(1004, "Elsa Jean", 35, phoneTask3s);

        ClientTask3 mia_khalifa = new ClientTask3(1005, "Mia Khalifa", 21, phoneTask3s);
        ClientTask3 lana_rhoades = new ClientTask3(1006, "Lana Rhoades", 23, phoneTask3s);
        ClientTask3 johnny_sins = new ClientTask3(1007, "Johnny Sins", 25, phoneTask3s);
        ClientTask3 piper_perri = new ClientTask3(1008, "Piper Perri", 18, phoneTask3s);

        ClientTask3 eva_elfie = new ClientTask3(1008, "Eva Elfie", 18, phoneTask3s);

        List<ClientTask3> clients = new ArrayList<>(Arrays.asList(
                chuckNorris,
                sasha_grey,
                sasha_grey2,
                elsa_jean,
                elsa_jean2,
                mia_khalifa,
                lana_rhoades,
                johnny_sins,
                eva_elfie,
                piper_perri
        ));


        Assert.assertEquals(53, getAgeSumByName(clients, "Sasha Grey"));

        Assert.assertEquals(
                new LinkedHashSet<>(Arrays.asList(
                        "Chuck Norris",
                        "Sasha Grey",
                        "Sasha Grey",
                        "Elsa Jean",
                        "Elsa Jean",
                        "Mia Khalifa",
                        "Lana Rhoades",
                        "Johnny Sins",
                        "Eva Elfie",
                        "Piper Perri"))
                , getNameSetOrdered(clients)
        );

        Assert.assertTrue(isClientOlderThan(clients, 39));
        Assert.assertFalse(isClientOlderThan(clients, Integer.MAX_VALUE));

        LinkedHashMap<Integer, String> expectedIdToName = new LinkedHashMap<>();
        expectedIdToName.put(chuckNorris.getId(), chuckNorris.getName());
        expectedIdToName.put(sasha_grey.getId(), sasha_grey.getName());
        expectedIdToName.put(sasha_grey2.getId(), sasha_grey2.getName());
        expectedIdToName.put(elsa_jean.getId(), elsa_jean.getName());
        expectedIdToName.put(elsa_jean2.getId(), elsa_jean2.getName());
        expectedIdToName.put(mia_khalifa.getId(), mia_khalifa.getName());
        expectedIdToName.put(lana_rhoades.getId(), lana_rhoades.getName());
        expectedIdToName.put(johnny_sins.getId(), johnny_sins.getName());
        expectedIdToName.put(piper_perri.getId(),"Eva Elfie, Piper Perri");

        Assert.assertEquals(expectedIdToName, convertListToIdToNameMap(clients));


        LinkedHashMap<Integer, List<ClientTask3>> expectedAgeToNames = new LinkedHashMap<>();
        expectedAgeToNames.put(chuckNorris.getAge(), Collections.singletonList(chuckNorris));
        expectedAgeToNames.put(18, Arrays.asList(sasha_grey, elsa_jean, eva_elfie, piper_perri));
        expectedAgeToNames.put(35, Arrays.asList(sasha_grey2, elsa_jean2));
        expectedAgeToNames.put(mia_khalifa.getAge(), Collections.singletonList(mia_khalifa));
        expectedAgeToNames.put(lana_rhoades.getAge(), Collections.singletonList(lana_rhoades));
        expectedAgeToNames.put(johnny_sins.getAge(), Collections.singletonList(johnny_sins));

        Assert.assertEquals(expectedAgeToNames, convertListToAgeToNamesMap(clients));

        Assert.assertTrue(
                Arrays.asList("111111,222222,11111111111,22222222222".split(","))
                        .containsAll(Arrays.asList(getAllTelephones(clients).split(",")))
        );

        Assert.assertEquals(sasha_grey2.getId(), getTheOldestOwnerPhone(clients).getId());

    }


    public static ClientTask3 createClient(int id) {

        List<String> names = Arrays.asList(
                "Sasha Grey",
                "Elsa Jean",
                "Mia Khalifa",
                "Lana Rhoades",
                "Johnny Sins",
                "Piper Perri"
        );
        Random random = new Random();
        String name = names.get(random.nextInt() % names.size());

        int age = Integer.parseInt(RandomStringUtils.randomNumeric(0, 150));

        String homeNumber1 = RandomStringUtils.randomNumeric(6);
        String homeNumber2 = RandomStringUtils.randomNumeric(6);
        String mobileNumber1 = RandomStringUtils.randomNumeric(11);
        String mobileNumber2 = RandomStringUtils.randomNumeric(11);

        List<PhoneTask3> phoneTask3s = Arrays.asList(
                new PhoneTask3(TelephoneTypeTask3.HOME_PHONE, homeNumber1),
                new PhoneTask3(TelephoneTypeTask3.HOME_PHONE, homeNumber2),
                new PhoneTask3(TelephoneTypeTask3.MOBILE_PHONE, mobileNumber1),
                new PhoneTask3(TelephoneTypeTask3.MOBILE_PHONE, mobileNumber2)
        );

        return new ClientTask3(id, name, age, phoneTask3s);

    }

}