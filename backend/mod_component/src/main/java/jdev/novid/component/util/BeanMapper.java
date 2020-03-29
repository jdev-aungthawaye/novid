package jdev.novid.component.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;

public class BeanMapper {

    private static final Logger LOG = LogManager.getLogger(BeanMapper.class);

    private ModelMapper modelMapper = new ModelMapper();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public BeanMapper() {

        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Condition nullable = new Condition() {

            public boolean applies(MappingContext context) {

                return true;

            }

        };

        this.modelMapper.getConfiguration().setPropertyCondition(nullable);
        this.modelMapper.getConfiguration().setFieldAccessLevel(AccessLevel.PRIVATE);
        this.modelMapper.getConfiguration().setMethodAccessLevel(AccessLevel.PRIVATE);
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    }

    public void map(Object source, Object destination) {

        this.modelMapper.map(source, destination);

    }

    public <S, D> D map(S source, Class<D> destinationType) {

        return this.modelMapper.map(source, destinationType);

    }

}