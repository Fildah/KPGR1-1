Průběžná úloha: Úsečka a n-úhelník

 * Vytvořte program pro kreslení úsečky zadané dvěma libovolnými koncovými body [x1,y1] a [x2,y2].
 * Koncové body úsečky zadávejte interaktivně: stisknutím tlačítka myši označte první vrchol, tažením kreslete pružnou čáru a uvolněním vykreslete finální úsečku.
 * Implementujte DDA algoritmus fungující pro všechny kvadranty.
 * Doplňte program tak, aby bylo možné zadat uzavřený n-úhelník, tzn. vrcholy n-úhelníku uložte do odpovídající datové struktury pro použití v dalších úlohách.
 * Vytvořte program pro kreslení pravidelného n-úhelníku zadaného třemi body. Prvním kliknutím zadejte střed opsané kružnice, druhým kliknutím bod na obvodu, tj. poloměr opsané kružnice a natočení n-úhelníku, třetím kliknutím určete délku strany, resp. velikost úhlu tvořící výseč jednoho segmentu n-uhelníku.
 * Minimální počet vrcholů n-úhelníku omezte na 3.
 * V průběhu kreslení n-úhelníku zobrazujte aktuální tvar útvaru.
 * Bonus: implementujte libovolný algoritmus vykreslující vyhlazenou (antialiasovanou) úsečku.
 * Při hodnocení je kladen důraz na funkčnost programu pro libovolně zadané koncové body, na přesnost vykreslení a na kvalitu návrhu a čitelnost kódu. Kód vhodně rozdělte do rozhraní a tříd. Kód očistěte od ladicích či pokusných nefunkčních částí.
 * Před odevzdáním si znovu přečtěte pravidla odevzdávání semestrálních projektů a průběžných úloh.
 * Můžete se inspirovat přiloženou ukázkou
 
 Průběžná úloha: Vyplnění a ořezání n-úhelníkové oblasti
 
  * Navažte na předchozí úlohu implementující zadávání a vykreslování uzavřeného polygonu (n-úhelníku).
  * Implementujte algoritmus semínkového vyplnění rastrově zadané oblasti.
  * Myší zadanou hranici oblasti vykreslete na rastrovou plochu plátna barvou odlišnou od barvy vyplnění.
  * Kliknutím vyberte počáteční pixel záplavového algoritmu a plochu vybarvěte
  * Uvažujte dvě možnosti hraniční podmínky vyplňování. Jednak omezení barvou pozadí a jednak barvou hranice.
  * Bonus: Při vyplňovaní rastrově zadané hranice implementujte také variantu vyplnění útvaru pravidelně se opakujícím vzorem zadaným čtvercovou maticí, stačí jako konstanta v kódu.
  * Implementujte algoritmus ořezání libovolného uzavřeného n-úhelníku konvexním n-úhelníkem. Oba útvary jsou zadány seznamem úseček tvořících jejich obvod (geometricky zadaná hranice).
  * Implementujte Scan-line algoritmus vyplnění plochy n-úhelníku, který je výsledkem ořezání v předchozím kroku.
  * Bonus: Doplňte možnost editace již zadaného n-úhelníku, změna pozice vrcholu, případně smazání stávajícího či přidání nového vrcholu.
  * Při hodnocení je důraz kladen na správné vyplnění oblasti a znázornění vykreslení oříznuté oblasti, na kvalitu návrhu a na efektivitu a čitelnost implementace. Vytvořte vhodné třídy implementující dané algoritmy.
  * Před odevzdáním si znovu přečtěte pravidla odevzdávání semestrálních projektů a průběžných úloh.
  * Můžete se inspirovat přiloženou ukázkou