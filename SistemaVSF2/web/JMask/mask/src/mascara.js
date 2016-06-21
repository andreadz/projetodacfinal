  
            // Mask cpf/cnpj
            jQuery(function ($) {
                $("#cpf").mask("999.999.999-99", {reverse: true});
                $("#cnpj").mask("99.999.999/9999-99", {reverse: true});                 
                $("#rg").mask("99.999.999-9", {reverse: true});   
                $("#telefone").mask("(99)?99999-9999", {reverse: true});   
                $("#cep").mask("99999-999", {reverse: true});   
                $("#agencia").mask("9999", {reverse: true});   
                $("#conta").mask("?99999-9", {reverse: true});   
                $("#agConta").mask("9999/?999999", {reverse: true});   
                
                
                
                // Esconde div radio
                $("#radioCPF").click(function ()
                {                    
                    if ($(this).val() === "F")
                    {                                                
                        $("#cnpj").hide();
                        $("#cpf").show();
                    } 
                });
                
                 $("#radioCNPJ").click(function ()
                {                    
                    if ($(this).val() === "J")
                    {                                                
                        $("#cpf").hide();
                        $("#cnpj").show();
                    } 
                });
                
            });
       
               
            



        