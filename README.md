# MobileChallengeSM

Se utilizó arquitectura MVVM puesto que es especialmente y actualmente recomendado por Android y Google, tiene ventajas para trabajar con las tecnológias mas recientes y componentes de android Jetpack (ciclos de vida, live data, observers) monitoreando de manera útil el ciclo de vida activities y fragmentos.

![image](https://user-images.githubusercontent.com/33889248/135734901-2037cda8-172f-40bd-b988-5869ce97de92.png)


El uso de arquitectura limpia y principios SOLID disponen de un seguimiento para tener facilidad al momento de escribir nuestras pruebas y tener mayor alcancé de objetivos a largo plazo dentro del proyecto.

![image](https://user-images.githubusercontent.com/33889248/135734911-00deb841-d631-4f38-a28f-b667f461e198.png)


También podemos observar que se cuenta con el uso de Flow para la emisión de valores de manera secuencial y tener datos asincronos.

Uso de paging 3 para tener flujo de datos unidireccional.

![image](https://user-images.githubusercontent.com/33889248/135734881-0f5aeaba-1c68-4636-b798-305eb15e2390.png)


¡NOTA!: Debido a tiempos las pruebas para el flujo completo de paging no han sido implementadas ya que esto requiere de pruebas instrumentadas...

DETALLES:

Arquitectura: 

- MVVM.

Lenguaje: 

- Kotlin.

Esta aplicación utiliza:

- Flow.

- Retrofit.

- Paging 3.

- Room.

- Dagger Hilt.

- Navigation.

- Mockk.

- Mock web server.
