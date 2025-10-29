package com.fase4.fiap.infraestructure.auxiliary.configuration.web;

import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.coletaEncomenda.gateway.ColetaEncomendaGateway;
import com.fase4.fiap.entity.message.notificacao.gateway.NotificacaoGateway;
import com.fase4.fiap.entity.message.notificacaoLeitura.gateway.NotificacaoLeituraGateway;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.recebimentoEncomenda.gateway.RecebimentoEncomendaGateway;
import com.fase4.fiap.infraestructure.apartamento.gateway.ApartamentoDatabaseGateway;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.*;
import com.fase4.fiap.infraestructure.coletaEncomenda.gateway.ColetaEncomendaDatabaseGateway;
import com.fase4.fiap.infraestructure.message.gateway.NotificacaoDatabaseGateway;
import com.fase4.fiap.infraestructure.message.gateway.NotificacaoLeituraDatabaseGateway;
import com.fase4.fiap.infraestructure.message.producer.NotificacaoProducer;
import com.fase4.fiap.infraestructure.morador.gateway.MoradorDatabaseGateway;
import com.fase4.fiap.infraestructure.recebimentoEncomenda.gateway.RecebimentoEncomendaDatabaseGateway;
import com.fase4.fiap.usecase.apartamento.CreateApartamentoUseCase;
import com.fase4.fiap.usecase.apartamento.DeleteApartamentoUseCase;
import com.fase4.fiap.usecase.apartamento.GetApartamentoUseCase;
import com.fase4.fiap.usecase.apartamento.SearchApartamentoUseCase;
import com.fase4.fiap.usecase.coletaEncomenda.CreateColetaEncomendaUseCase;
import com.fase4.fiap.usecase.coletaEncomenda.GetColetaEncomendaUseCase;
import com.fase4.fiap.usecase.coletaEncomenda.SearchColetaEncomendaUseCase;
import com.fase4.fiap.usecase.message.NotificarLeituraUseCase;
import com.fase4.fiap.usecase.message.publish.PublicarNotificacaoUseCase;
import com.fase4.fiap.usecase.morador.*;
import com.fase4.fiap.usecase.recebimentoEncomenda.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ResourceBundle;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }

    @Bean
    public ResourceBundle messageBundle() {
        return ResourceBundle.getBundle("ValidationMessages");
    }

    @Bean
    public LocaleResolver sessionLocaleResolver() {
        return new AcceptHeaderResolver();
    }

    // --- Apartamento ---
    @Bean
    public CreateApartamentoUseCase createApartamentoUseCase(ApartamentoRepository apartamentoRepository) {
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new CreateApartamentoUseCase(apartamentoGateway);
    }

    @Bean
    public DeleteApartamentoUseCase deleteApartamentoUseCase(ApartamentoRepository apartamentoRepository) {
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new DeleteApartamentoUseCase(apartamentoGateway);
    }

    @Bean
    public GetApartamentoUseCase getApartamentoUseCase(ApartamentoRepository apartamentoRepository) {
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new GetApartamentoUseCase(apartamentoGateway);
    }

    @Bean
    public SearchApartamentoUseCase searchApartamentoUseCase(ApartamentoRepository apartamentoRepository) {
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new SearchApartamentoUseCase(apartamentoGateway);
    }

    // --- ColetaEncomenda ---
    @Bean
    public CreateColetaEncomendaUseCase createColetaEncomendaUseCase(ColetaEncomendaRepository coletaEncomendaRepository) {
        ColetaEncomendaGateway coletaEncomendaGateway = new ColetaEncomendaDatabaseGateway(coletaEncomendaRepository);
        return new CreateColetaEncomendaUseCase(coletaEncomendaGateway);
    }

    @Bean
    public GetColetaEncomendaUseCase getColetaEncomendaUseCase(ColetaEncomendaRepository coletaEncomendaRepository) {
        ColetaEncomendaGateway coletaEncomendaGateway = new ColetaEncomendaDatabaseGateway(coletaEncomendaRepository);
        return new GetColetaEncomendaUseCase(coletaEncomendaGateway);
    }

    @Bean
    public SearchColetaEncomendaUseCase searchColetaEncomendaUseCase(ColetaEncomendaRepository coletaEncomendaRepository) {
        ColetaEncomendaGateway coletaEncomendaGateway = new ColetaEncomendaDatabaseGateway(coletaEncomendaRepository);
        return new SearchColetaEncomendaUseCase(coletaEncomendaGateway);
    }

    // --- Morador ---
    @Bean
    public CreateMoradorUseCase createMoradorUseCase(MoradorRepository moradorRepository,
                                                     ApartamentoRepository apartamentoRepository) {
        MoradorGateway moradorGateway = new MoradorDatabaseGateway(moradorRepository);
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new CreateMoradorUseCase(moradorGateway, apartamentoGateway);
    }

    @Bean
    public DeleteMoradorUseCase deleteMoradorUseCase(MoradorRepository moradorRepository) {
        MoradorGateway moradorGateway = new MoradorDatabaseGateway(moradorRepository);
        return new DeleteMoradorUseCase(moradorGateway);
    }

    @Bean
    public GetMoradorUseCase getMoradorUseCase(MoradorRepository moradorRepository) {
        MoradorGateway moradorGateway = new MoradorDatabaseGateway(moradorRepository);
        return new GetMoradorUseCase(moradorGateway);
    }

    @Bean
    public SearchMoradorUseCase searchMoradorUseCase(MoradorRepository moradorRepository) {
        MoradorGateway moradorGateway = new MoradorDatabaseGateway(moradorRepository);
        return new SearchMoradorUseCase(moradorGateway);
    }

    @Bean
    public UpdateMoradorUseCase updateMoradorUseCase(MoradorRepository moradorRepository,
                                                     ApartamentoRepository apartamentoRepository) {
        MoradorGateway moradorGateway = new MoradorDatabaseGateway(moradorRepository);
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new UpdateMoradorUseCase(moradorGateway, apartamentoGateway);
    }

    // --- RecebimentoEncomenda ---
    @Bean
    public CreateRecebimentoEncomendaUseCase createRecebimentoEncomendaUseCase(RecebimentoEncomendaRepository recebimentoEncomendaRepository,
                                                                               ApartamentoRepository apartamentoRepository,
                                                                               PublicarNotificacaoUseCase publicarNotificacaoUseCase) {
        RecebimentoEncomendaGateway recebimentoEncomendaGateway = new RecebimentoEncomendaDatabaseGateway(recebimentoEncomendaRepository);
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new CreateRecebimentoEncomendaUseCase(recebimentoEncomendaGateway, apartamentoGateway, publicarNotificacaoUseCase);
    }

    @Bean
    public GetRecebimentoEncomendaUseCase getRecebimentoEncomendaUseCase(RecebimentoEncomendaRepository recebimentoEncomendaRepository) {
        RecebimentoEncomendaGateway recebimentoEncomendaGateway = new RecebimentoEncomendaDatabaseGateway(recebimentoEncomendaRepository);
        return new GetRecebimentoEncomendaUseCase(recebimentoEncomendaGateway);
    }

    @Bean
    public SearchByApartamentoRecebimentoEncomendaUseCase searchByApartamentoRecebimentoEncomendaUseCase(RecebimentoEncomendaRepository recebimentoEncomendaRepository) {
        RecebimentoEncomendaGateway recebimentoEncomendaGateway = new RecebimentoEncomendaDatabaseGateway(recebimentoEncomendaRepository);
        return new SearchByApartamentoRecebimentoEncomendaUseCase(recebimentoEncomendaGateway);
    }

    @Bean
    public SearchRecebimentoEncomendaUseCase searchRecebimentoEncomendaUseCase(RecebimentoEncomendaRepository recebimentoEncomendaRepository) {
        RecebimentoEncomendaGateway recebimentoEncomendaGateway = new RecebimentoEncomendaDatabaseGateway(recebimentoEncomendaRepository);
        return new SearchRecebimentoEncomendaUseCase(recebimentoEncomendaGateway);
    }

    @Bean
    public UpdateRecebimentoEncomendaUseCase updateRecebimentoEncomendaUseCase(RecebimentoEncomendaRepository recebimentoEncomendaRepository) {
        RecebimentoEncomendaGateway recebimentoEncomendaGateway = new RecebimentoEncomendaDatabaseGateway(recebimentoEncomendaRepository);
        return new UpdateRecebimentoEncomendaUseCase(recebimentoEncomendaGateway);
    }

    // --- Message ---
    @Bean
    public PublicarNotificacaoUseCase publicarNotificacaoUseCase(NotificacaoRepository notificacaoRepository,
                                                                 NotificacaoProducer notificacaoProducer) {
        NotificacaoGateway notificacaoGateway = new NotificacaoDatabaseGateway(
                notificacaoRepository,
                notificacaoProducer
        );
        return new PublicarNotificacaoUseCase(notificacaoGateway);
    }

    @Bean
    public NotificarLeituraUseCase notificarLeituraUseCase(NotificacaoLeituraGateway notificacaoLeituraGateway,
                                                           NotificacaoGateway notificacaoGateway) {
        return new NotificarLeituraUseCase(notificacaoLeituraGateway, notificacaoGateway);
    }

    @Bean
    public NotificacaoLeituraGateway notificacaoLeituraGateway(NotificacaoLeituraRepository repository) {
        return new NotificacaoLeituraDatabaseGateway(repository);
    }

    @Bean
    public NotificacaoGateway notificacaoGateway(
            NotificacaoRepository notificacaoRepository,
            NotificacaoProducer notificacaoProducer) {
        return new NotificacaoDatabaseGateway(notificacaoRepository, notificacaoProducer);
    }

}