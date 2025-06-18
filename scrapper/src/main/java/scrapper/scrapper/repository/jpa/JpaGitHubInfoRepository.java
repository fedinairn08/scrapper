package scrapper.scrapper.repository.jpa;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import scrapper.scrapper.entity.GitHubInfo;
import scrapper.scrapper.entity.Link;

import java.util.Optional;

public interface JpaGitHubInfoRepository extends JpaRepository<GitHubInfo, Long> {
    GitHubInfo findByLink_Id(Long linkId);

    @NotNull Optional<GitHubInfo> findById(@NotNull Long id);

    void deleteByLink(Link link);
}
