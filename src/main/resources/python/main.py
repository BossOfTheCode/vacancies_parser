from collections import Counter

import matplotlib.pyplot as plt
import os
import sys


def main():
    all_data = get_data_from_file(sys.argv[1])
    title = get_title_from_filename(sys.argv[1])
    data = dict(Counter(all_data).most_common(get_skill_count(all_data, int(sys.argv[3]))))
    draw_plot(data, sys.argv[2], title=title)


def get_skill_count(data, top=10):
    return top if len(data) >= top else len(data)


def get_title_from_filename(filename):
    return os.path.splitext(os.path.basename(filename))[0]


def get_data_from_file(filename):
    with open(filename, encoding='utf-8') as file:
        lines = file.readlines()
        data = {str(line.split(';', maxsplit=1)[0]): int(line.split(';', maxsplit=1)[1])
                for line in lines}
        return data


def draw_plot(data_dict, output, title='', width=0.75, colormap_name="viridis"):
    fig, ax = plt.subplots()

    ind = [x for x in range(len(data_dict.values()))]

    cmap = plt.get_cmap(colormap_name)

    ax.barh(ind,
            data_dict.values(),
            width,
            color=cmap.colors)

    ax.set_yticks(list(map(lambda x: x + width / 2, ind)))
    ax.set_yticklabels(data_dict.keys(), minor=False)
    ax.set_title(title)

    plt.savefig(output,
                dpi=300,
                format='png',
                bbox_inches='tight')


if __name__ == "__main__":
    main()