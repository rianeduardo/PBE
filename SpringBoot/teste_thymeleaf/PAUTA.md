# Anotações aula Thymeleaf

Thymeleaf é uma ferramenta do springboot que nos permite fazer a interação com documentos estáticos (HTML/CSS) diretamente do nosso back-end em Java, sem necessidade de escrever um JavaScript por fora por exemplo

**1.** -> Inclusão do Thymelaf no documento HTML:

````<html lang="pt-BR" xmlns:th="http://thymeleaf.org">````

**2.** -> Iniciar o documento, h1 fixo, e span dinâmico, que vai pegar uma mensagem, que podemos observar como valor no atributo ``th:text="${msg}"``

````
<h1>Seja bem-vindo ao nosso site!</h1>
<span th:text="${msg}"></span>
````


Num primeiro momento, se rodarmos o app assim, o valor de span nem vai existir, pois não temos integração com back-end, consequentemente não estamos recebendo o valor ``msg``

**3.** -> Configuração do controller Index no back-end

````
@Controller
public class indexController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView abrirIndex() {
        ModelAndView mv = new ModelAndView("index");
        String msg = "Olá, Visitante!";
        mv.addObject("msg", msg);
        return mv;
    }
}
````

Essa é nossa classe de controller da página Index, ela tem uma função ``abrirIndex()`` apenas

O que esse bloco faz?

````
@Controller
````

-> É uma anotação ao Java, de que estamos construindo uma classe de um controller

````
public class IndexController {}
````

Cria a classe publica IndexController

````
@RequestMapping(value = "/", method = RequestMethod.GET)
````

O que vai acontecer quando o usuário acessar o / do nosso site, com o método GET

````
public ModelAndView abrirIndex() {}
````

Cria uma função que retornára um valor ModelAndView, essa classe ModelAndView é o que permite o loading do HTML via Thymeleaf, então veja ele como uma classe "Tela"

````
ModelAndView mv = new ModelAndView("index");
        String msg = "Olá, Visitante!";
        mv.addObject("msg", msg);
        return mv;
````

Criamos um objeto ModelAndView, chamado mv, e ele vai carregar os valores do index.html, criamos uma String msg, e adicionamos esse objeto msg a ModelAndView, que o Thymeleaf reconhece lá no nosso arquivo como ``${msg}``

---
**Navegação**

````
    @GetMapping("/sobre")
    public String abrirSobre() {
        return "sobre";
    }

    @GetMapping("/produto")
    public String abrirProdutos() {
        return "produtos";
    }

    @GetMapping("/contato")
    public String abrirContato() {
        return "contato";
    }
````

Então seguindo a mesma lógica, a gente vai criando rotas, então quando o usuário mandar um GET, em tal link, ele retorna uma ModelAndView que tem dentro de si a página HTML do respectivo link, a notação @GetMapping faz isso ser mais fácil, não temos que criar o Model, e tudo mais

Mas ele faz a mesma coisa que o @RequestMapping, só use @RequestMapping se for pra coisas mais complicadas, rotas em si use o @GetMapping

---
**POST**

Adicionamos um input de nome no nosso index.html:

````
    <form action="/home" method="POST">
        <label for="nome">Insira seu nome:</label>
        <input type="text" name="nome" id="nome">
        <button type="submit">ENVIAR</button>
    </form>
````

No controller:

````
    @PostMapping("/home")
    public ModelAndView postHome(@RequestParam("nome") String nome) {
        ModelAndView mvInput = new ModelAndView("index");
        String mensagemInput = "Olá, " + nome + "!";
        mvInput.addObject("msg", mensagemInput);
        mvInput.addObject("nome", "");
        return mvInput;
    }
````

Essa notação é igual a @GetMapping, só que como estamos usando POST (Enviar dados pro back-end) vamos usar o @PostMapping, na url que recebe os valores do form

Função ModelAndView tem como os parametros ``@RequestParam("nome") String nome``, essa linha transforma o dado que vier no POST com atributo "nome", pra uma String nome

Criamos uma mv para carregar o index.html

E agora nossa mensagem, ao em vez de receber "Olá, Visitante", vai receber ``"Olá, " + nome``

Adicionamos o objeto ao index.html, e apagamos o input logo após isso, e retornamos a mv

